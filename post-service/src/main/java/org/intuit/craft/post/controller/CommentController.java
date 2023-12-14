package org.intuit.craft.post.controller;

import jakarta.validation.Valid;
import org.intuit.craft.post.model.CommentRequest;
import org.intuit.craft.post.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("comments")
@Validated
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@Valid @RequestBody CommentRequest commentRequest, @RequestHeader("user_id") String userId) {
        commentService.addComment(commentRequest, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@Valid @RequestBody CommentRequest commentRequest, @RequestHeader("user_id") String userId) {
        commentService.updateComment(commentRequest, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID id, @RequestHeader("user_id") String userId) {
        commentService.deleteComment(id, userId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
