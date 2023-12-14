package com.intuit.craft.view.service;

import org.intuit.craft.dto.CommentResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CommentService {

    Page<CommentResponse> getReplies(UUID parentId, int page, int size);

    Page<CommentResponse> getLatestComments(int page, int size);
}
