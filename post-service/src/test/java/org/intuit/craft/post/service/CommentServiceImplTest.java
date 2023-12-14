package org.intuit.craft.post.service;

import org.intuit.craft.post.config.MessagingConfig;
import org.intuit.craft.post.ebs.EventPublisher;
import org.intuit.craft.post.exception.InvalidInputException;
import org.intuit.craft.post.model.CommentRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommentServiceImplTest {

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private MessagingConfig messagingConfig;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddComment() {
        CommentRequest commentRequest = new CommentRequest();
        String userId = "user123";

        commentService.addComment(commentRequest, userId);

        verify(eventPublisher, times(1)).publish(any(), any(), any());
    }

    @Test
    public void testUpdateComment() {
        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setId(UUID.randomUUID());
        String userId = "user123";

        commentService.updateComment(commentRequest, userId);

        verify(eventPublisher, times(1)).publish(any(), any(), any());
    }

    @Test
    public void testUpdateCommentWithNullId() {
        CommentRequest commentRequest = new CommentRequest();
        String userId = "user123";

        // Assert
        InvalidInputException exception = Assertions.assertThrows(InvalidInputException.class,
                () -> commentService.updateComment(commentRequest, userId));
        Assertions.assertEquals("commentId cannot be null", exception.getMessage());
    }

    @Test
    public void testDeleteComment() {
        UUID commentId = UUID.randomUUID();
        String userId = "user123";

        commentService.deleteComment(commentId, userId);

        verify(eventPublisher, times(1)).publish(any(), any(), any());
    }
}
