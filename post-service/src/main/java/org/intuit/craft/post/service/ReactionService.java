package org.intuit.craft.post.service;

import org.intuit.craft.post.model.ReactionRequest;

public interface ReactionService {

    void submitReaction(ReactionRequest reactionRequest, String userId);
}
