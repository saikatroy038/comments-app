package org.intuit.craft.post.service;

import org.intuit.craft.post.model.CommentRequest;

import java.util.UUID;

public interface CommentService {

    void addComment(CommentRequest commentRequest, String userId);

    void updateComment(CommentRequest commentRequest, String userId);

    void deleteComment(UUID commentId, String userId);
}
