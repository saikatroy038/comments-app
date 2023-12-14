package com.intuit.craft.view.controller;

import com.intuit.craft.view.service.CommentService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.intuit.craft.dto.CommentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping
    public Page<CommentResponse> getComments(@RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size) {
        return commentService.getLatestComments(page, size);
    }

    @GetMapping("/{parentId}")
    public Page<CommentResponse> getReplies(@PathVariable UUID parentId,
                                     @RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(1) @Max(value = 100, message = "maximum 100 page size allowed") int size) {
        return commentService.getReplies(parentId, page, size);
    }
}
