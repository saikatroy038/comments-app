package org.intuit.craft.processor.mapper;

import org.intuit.craft.entity.CommentEntity;
import org.intuit.craft.model.CommentStatus;
import org.intuit.craft.model.event.Comment;

public class CommentMapper {

    public static CommentEntity getCommentEntityFromComment(Comment comment) {
        return CommentEntity.builder()
                .content(comment.getContent())
                .status(CommentStatus.ACTIVE)
                .hasReplies(false)
                .parentId((comment.getParentId() == null) ? null : comment.getParentId())
                .userId(comment.getUserId())
                .build();
    }
}
