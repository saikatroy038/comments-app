package org.intuit.craft.post.mapper;

import org.junit.jupiter.api.Test;
import org.intuit.craft.model.event.Comment;
import org.intuit.craft.post.model.CommentRequest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    @Test
    public void testGetCommentFromCommentRequest() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setId(UUID.randomUUID());
        commentRequest.setContent("Sample content");
        commentRequest.setParentId(UUID.randomUUID());
        String userId = "user123";

        Comment comment = CommentMapper.getCommentFromCommentRequest(commentRequest, userId);

        assertEquals(commentRequest.getId(), comment.getId());
        assertEquals(commentRequest.getContent(), comment.getContent());
        assertEquals(commentRequest.getParentId(), comment.getParentId());
        assertEquals(userId, comment.getUserId());
    }
}

