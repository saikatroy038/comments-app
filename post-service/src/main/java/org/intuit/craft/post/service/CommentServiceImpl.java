package org.intuit.craft.post.service;

import org.intuit.craft.model.event.Comment;
import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.post.config.MessagingConfig;
import org.intuit.craft.post.ebs.EventPublisher;
import org.intuit.craft.post.exception.InvalidInputException;
import org.intuit.craft.post.mapper.CommentMapper;
import org.intuit.craft.post.model.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private MessagingConfig messagingConfig;

    public void addComment(CommentRequest commentRequest, String userId) {
        commentRequest.setId(null);
        publishCommentEvent(commentRequest, userId, EventType.ADD_COMMENT);
    }

    public void updateComment(CommentRequest commentRequest, String userId) {
        if (commentRequest.getId() == null) {
            throw new InvalidInputException("commentId cannot be null");
        }
        publishCommentEvent(commentRequest, userId, EventType.UPDATE_COMMENT);
    }

    public void deleteComment(UUID commentId, String userId) {
        publishCommentEvent(CommentRequest.builder().id(commentId).build(), userId, EventType.DELETE_COMMENT);
    }

    private void publishCommentEvent(CommentRequest commentRequest, String userId, EventType updateComment) {
        Comment comment = CommentMapper.getCommentFromCommentRequest(commentRequest, userId);
        Event<Comment> event = new Event<>();
        event.setType(updateComment);
        event.setData(comment);
        eventPublisher.publish(messagingConfig.getEmptyExchange(),
                messagingConfig.getCommentsRoutingKey(), event);
    }
}
