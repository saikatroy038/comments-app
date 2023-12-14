package org.intuit.craft.post.controller;

import jakarta.validation.Valid;
import org.intuit.craft.post.model.ReactionRequest;
import org.intuit.craft.post.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reactions")
@Validated
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping
    public ResponseEntity<Void> react(@Valid @RequestBody ReactionRequest reaction, @RequestHeader("user_id") String userId) {
        reactionService.submitReaction(reaction, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
