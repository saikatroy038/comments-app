package org.intuit.craft.post.controller;

import org.intuit.craft.post.model.CommentRequest;
import org.intuit.craft.post.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddComment() {
        CommentRequest commentRequest = new CommentRequest();
        String userId = "user123";

        ResponseEntity<Void> response = commentController.addComment(commentRequest, userId);

        verify(commentService, times(1)).addComment(commentRequest, userId);
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testUpdateComment() {
        CommentRequest commentRequest = new CommentRequest();
        String userId = "user123";

        ResponseEntity<Void> response = commentController.updateComment(commentRequest, userId);

        verify(commentService, times(1)).updateComment(commentRequest, userId);
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    public void testDeleteComment() {
        UUID commentId = UUID.randomUUID();
        String userId = "user123";

        ResponseEntity<Void> response = commentController.deleteComment(commentId, userId);

        verify(commentService, times(1)).deleteComment(commentId, userId);
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}
