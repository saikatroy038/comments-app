package com.intuit.craft.view.service;

import org.intuit.craft.dto.ReactionResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReactionService {

    Page<ReactionResponse> getReactions(UUID commentId, int page, int size);
}
