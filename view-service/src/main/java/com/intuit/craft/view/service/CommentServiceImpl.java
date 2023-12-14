package com.intuit.craft.view.service;

import com.intuit.craft.view.exception.RecordNotFoundException;
import org.intuit.craft.dto.CommentResponse;
import org.intuit.craft.repository.CommentReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentServiceImpl implements CommentService {

    private static final Sort DEFAULT_SORT = Sort.by("updatedAt").descending();

    @Autowired
    private CommentReadRepository commentReadRepository;

    @Override
    public Page<CommentResponse> getReplies(UUID parentId, int page, int size) {
        if (!commentReadRepository.existsById(parentId)) {
            throw new RecordNotFoundException("Comment not found");
        }
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        Page<CommentResponse> commentResponsePage = commentReadRepository.findCommentsByParentId(parentId, pageable);
        hideDeletedCommentsContent(commentResponsePage);
        return commentResponsePage;
    }

    @Override
    public Page<CommentResponse> getLatestComments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        Page<CommentResponse> commentResponsePage = commentReadRepository.findFirstLevelComments(pageable);
        hideDeletedCommentsContent(commentResponsePage);
        return commentResponsePage;
    }

    private void hideDeletedCommentsContent(Page<CommentResponse> commentResponsePage) {
        for (CommentResponse commentResponse : commentResponsePage) {
            commentResponse.hideDeleted();
        }
    }
}
