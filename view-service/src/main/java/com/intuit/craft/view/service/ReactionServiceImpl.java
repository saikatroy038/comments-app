package com.intuit.craft.view.service;

import com.intuit.craft.view.exception.RecordNotFoundException;
import org.intuit.craft.dto.ReactionResponse;
import org.intuit.craft.repository.CommentReactionsMappingReadRepository;
import org.intuit.craft.repository.CommentReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private CommentReactionsMappingReadRepository commentReactionsMappingReadRepository;

    @Autowired
    private CommentReadRepository commentReadRepository;

    @Override
    public Page<ReactionResponse> getReactions(UUID commentId, int page, int size) {
        if (!commentReadRepository.existsById(commentId)) {
            throw new RecordNotFoundException("comment not found");
        }
        return commentReactionsMappingReadRepository.getReactions(commentId, PageRequest.of(page, size));
    }
}
