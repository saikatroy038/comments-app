package org.intuit.craft.processor.worker;

import org.intuit.craft.entity.CommentReactionsMappingEntity;
import org.intuit.craft.model.ReactionStatus;
import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.model.event.Reaction;
import org.intuit.craft.repository.CommentReactionsMappingReadWriteRepository;
import org.intuit.craft.repository.CommentReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class AddReactionWorker implements Worker<Reaction> {

    @Autowired
    private CommentReactionsMappingReadWriteRepository reactionRepository;

    @Autowired
    private CommentReadRepository commentRepository;

    @Transactional
    @Override
    public void process(Event<Reaction> event) {
        Reaction reaction = event.getData();
        if (commentRepository.existsById(reaction.getCommentId())) {
            Optional<CommentReactionsMappingEntity> optionalReactionEntity =
                    reactionRepository.findByCommentIdAndUserId(reaction.getCommentId(), reaction.getUserId());
            if (optionalReactionEntity.isPresent()) {
                // update the existing reaction
                CommentReactionsMappingEntity commentReactionsMappingEntity = optionalReactionEntity.get();
                if (!commentReactionsMappingEntity.getReaction().equals(event.getData().getReaction())) {
                    // update reaction only if it has changed
                    commentReactionsMappingEntity.setPrevReaction(commentReactionsMappingEntity.getReaction());
                    commentReactionsMappingEntity.setReaction(reaction.getReaction());
                    commentReactionsMappingEntity.setStatus(ReactionStatus.NEW);
                    reactionRepository.save(commentReactionsMappingEntity);
                }
            } else {
                // reaction doesn't exist, add new
                CommentReactionsMappingEntity commentReactionsMappingEntity = CommentReactionsMappingEntity.builder()
                        .commentId(reaction.getCommentId())
                        .prevReaction(org.intuit.craft.model.Reaction.NONE)
                        .reaction(reaction.getReaction())
                        .userId(reaction.getUserId())
                        .status(ReactionStatus.NEW)
                        .build();
                reactionRepository.save(commentReactionsMappingEntity);
            }
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD_REACTION;
    }
}
