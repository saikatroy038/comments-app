package org.intuit.craft.processor.jobs;

import org.intuit.craft.entity.CommentReactionsMappingEntity;
import org.intuit.craft.entity.ReactionCountEntity;
import org.intuit.craft.model.Reaction;
import org.intuit.craft.model.ReactionStatus;
import org.intuit.craft.repository.CommentReactionsMappingReadWriteRepository;
import org.intuit.craft.repository.CommentReadRepository;
import org.intuit.craft.repository.ReactionCountReadWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class ReactionCounter {

    private static final long INITIAL_DELAY = 60 * 1000;

    private static final long UPDATE_REACTIONS_COUNT_RATE = 60 * 1000;

    private static final int BATCH_SIZE = 500;

    @Autowired
    private ReactionCountReadWriteRepository reactionCountRepo;

    @Autowired
    private CommentReactionsMappingReadWriteRepository reactionsRepo;

    @Autowired
    private CommentReadRepository commentReadRepository;

    @Transactional
    @Scheduled(fixedRate = UPDATE_REACTIONS_COUNT_RATE, initialDelay = INITIAL_DELAY)
    public void updateReactionsCount() {
        try {
            List<CommentReactionsMappingEntity> commentReactionsMappings =
                    reactionsRepo.findAndUpdateNewStatusWithLock(ReactionStatus.NEW, PageRequest.of(0, BATCH_SIZE));
            Map<UUID, Map<Reaction, Integer>> commentToReactionsCount = new HashMap<>();
            Set<UUID> commentIds = new HashSet<>();
            for (CommentReactionsMappingEntity crm : commentReactionsMappings) {
                commentIds.add(crm.getCommentId());
                Map<Reaction, Integer> reactionCount = commentToReactionsCount.getOrDefault(crm.getCommentId(), new HashMap<>());
                if (!Reaction.NONE.equals(crm.getReaction())) {
                    reactionCount.put(crm.getReaction(), reactionCount.getOrDefault(crm.getReaction(), 0) + 1);
                }
                if (!Reaction.NONE.equals(crm.getPrevReaction())) {
                    reactionCount.put(crm.getPrevReaction(), reactionCount.getOrDefault(crm.getPrevReaction(), 0) - 1);
                }
                commentToReactionsCount.put(crm.getCommentId(), reactionCount);
            }

            // get all reactionCounts for updated comments
            List<ReactionCountEntity> reactionCountEntities = reactionCountRepo.findEntriesWithCommentIdsAndLockTable(commentIds);
            for (ReactionCountEntity reactionCountEntity : reactionCountEntities) {
                Map<Reaction, Integer> reactionCount = commentToReactionsCount.get(reactionCountEntity.getCommentId());
                if (reactionCount.containsKey(Reaction.LIKE)) {
                    long currentLike = (reactionCountEntity.getLikes() == null) ? 0 : reactionCountEntity.getLikes();
                    reactionCountEntity.setLikes(currentLike + reactionCount.get(Reaction.LIKE));
                }
                if (reactionCount.containsKey(Reaction.DISLIKE)) {
                    long currentDislike = (reactionCountEntity.getDisLikes() == null) ? 0 : reactionCountEntity.getDisLikes();
                    reactionCountEntity.setDisLikes(currentDislike + reactionCount.get(Reaction.DISLIKE));
                }
                commentToReactionsCount.remove(reactionCountEntity.getCommentId());
            }

            // add new reactionCountEntities
            for (UUID commentId : commentToReactionsCount.keySet()) {
                long likesCount = (commentToReactionsCount.get(commentId).get(Reaction.LIKE)) == null ? 0 : commentToReactionsCount.get(commentId).get(Reaction.LIKE);
                long dislikesCount = (commentToReactionsCount.get(commentId).get(Reaction.DISLIKE)) == null ? 0 : commentToReactionsCount.get(commentId).get(Reaction.DISLIKE);
                ReactionCountEntity reactionCountEntity = ReactionCountEntity.builder()
                        .commentId(commentId)
                        .likes(likesCount)
                        .disLikes(dislikesCount)
                        .build();
                reactionCountEntities.add(reactionCountEntity);
            }

            for (CommentReactionsMappingEntity crm : commentReactionsMappings) {
                crm.setStatus(ReactionStatus.UPDATED);
            }
            reactionsRepo.saveAll(commentReactionsMappings);
            reactionCountRepo.saveAll(reactionCountEntities);
            System.out.println("ReactionCount updated");
        } catch (Exception e) {
            System.out.println("Exception running updateReactionsCount" + e.getMessage());
        }
    }
}
