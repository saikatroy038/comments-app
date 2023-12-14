package com.intuit.craft.view.controller;

import com.intuit.craft.view.service.ReactionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.intuit.craft.dto.ReactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping("reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @GetMapping("/{commentId}")
    public Page<ReactionResponse> getReactions(@PathVariable("commentId") UUID commentId,
                                               @RequestParam(defaultValue = "0") @Min(0) int page,
                                               @RequestParam(defaultValue = "50") @Min(1) @Max(1000) int size) {
        return reactionService.getReactions(commentId, page, size);
    }
}
