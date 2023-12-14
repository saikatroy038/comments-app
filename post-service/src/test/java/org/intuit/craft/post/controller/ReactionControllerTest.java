package org.intuit.craft.post.controller;

import org.intuit.craft.post.model.ReactionRequest;
import org.intuit.craft.post.service.ReactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.*;

public class ReactionControllerTest {

    @Mock
    private ReactionService reactionService;

    @InjectMocks
    private ReactionController reactionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testReact() {
        ReactionRequest reactionRequest = new ReactionRequest();
        String userId = "user123";

        ResponseEntity<Void> response = reactionController.react(reactionRequest, userId);

        verify(reactionService, times(1)).submitReaction(reactionRequest, userId);
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }
}

