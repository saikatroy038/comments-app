package org.intuit.craft.post.service;

import org.intuit.craft.post.config.MessagingConfig;
import org.intuit.craft.post.ebs.EventPublisher;
import org.intuit.craft.post.model.ReactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class ReactionServiceImplTest {

    @Mock
    private EventPublisher eventPublisher;

    @Mock
    private MessagingConfig messagingConfig;

    @InjectMocks
    private ReactionServiceImpl reactionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSubmitReaction() {
        ReactionRequest reactionRequest = new ReactionRequest();
        String userId = "user123";

        reactionService.submitReaction(reactionRequest, userId);
        verify(eventPublisher, times(1)).publish(any(), any(), any());
    }
}
