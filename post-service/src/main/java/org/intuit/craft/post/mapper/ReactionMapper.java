package org.intuit.craft.post.mapper;

import org.intuit.craft.model.event.Reaction;
import org.intuit.craft.post.model.ReactionRequest;

import java.util.UUID;

public class ReactionMapper {

    public static Reaction getReactionFromReactionRequest(ReactionRequest reactionRequest, String userId) {
        return Reaction.builder()
                .commentId(reactionRequest.getCommentId())
                .userId(userId)
                .reaction(reactionRequest.getReaction())
                .build();
    }
}
