package org.intuit.craft.post.mapper;

import org.intuit.craft.model.event.Reaction;
import org.intuit.craft.post.model.ReactionRequest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReactionMapperTest {

    @Test
    public void testGetReactionFromReactionRequest() {
        ReactionRequest reactionRequest = new ReactionRequest();
        reactionRequest.setCommentId(UUID.randomUUID());
        reactionRequest.setReaction(org.intuit.craft.model.Reaction.LIKE);
        String userId = "user123";

        Reaction reaction = ReactionMapper.getReactionFromReactionRequest(reactionRequest, userId);

        assertEquals(reactionRequest.getCommentId(), reaction.getCommentId());
        assertEquals(reactionRequest.getReaction(), reaction.getReaction());
        assertEquals(userId, reaction.getUserId());
    }
}
