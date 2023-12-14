package org.intuit.craft.post.mapper;

import org.intuit.craft.model.event.Comment;
import org.intuit.craft.post.model.CommentRequest;

import java.util.UUID;

public class CommentMapper {

    public static Comment getCommentFromCommentRequest(CommentRequest commentRequest, String userId) {
        return Comment.builder()
                .id(commentRequest.getId())
                .content(commentRequest.getContent())
                .parentId(commentRequest.getParentId())
                .userId(userId)
                .build();
    }
}
