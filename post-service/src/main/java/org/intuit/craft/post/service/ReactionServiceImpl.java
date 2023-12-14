package org.intuit.craft.post.service;

import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.model.event.Reaction;
import org.intuit.craft.post.config.MessagingConfig;
import org.intuit.craft.post.ebs.EventPublisher;
import org.intuit.craft.post.mapper.ReactionMapper;
import org.intuit.craft.post.model.ReactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private MessagingConfig messagingConfig;

    @Override
    public void submitReaction(ReactionRequest reactionRequest, String userId) {
        Reaction reaction = ReactionMapper.getReactionFromReactionRequest(reactionRequest, userId);
        Event<Reaction> event = new Event<>();
        event.setData(reaction);
        event.setType(EventType.ADD_REACTION);
        eventPublisher.publish(messagingConfig.getEmptyExchange(), messagingConfig.getLikesRoutingKey(), event);
    }
}
