package org.intuit.craft.processor.worker;

import org.intuit.craft.entity.CommentEntity;
import org.intuit.craft.model.CommentStatus;
import org.intuit.craft.model.event.Comment;
import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.repository.CommentReadWriteRepository;
import org.intuit.craft.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class DeleteCommentWorker implements Worker<Comment> {

    @Autowired
    private CommentReadWriteRepository commentReadWriteRepository;

    @Transactional
    @Override
    public void process(Event<Comment> event) {
        Optional<CommentEntity> optionalCommentEntity = commentReadWriteRepository.findById(event.getData().getId());
        if (optionalCommentEntity.isPresent()
                && optionalCommentEntity.get().getUserId().equals(event.getData().getUserId().toString())) {
            CommentEntity commentEntity = optionalCommentEntity.get();
            commentEntity.setStatus(CommentStatus.DELETED);
            commentEntity.setUpdatedAt(LocalDateTimeUtil.getLocalDateTimeFromString(event.getCreatedAt()));
            commentReadWriteRepository.save(commentEntity);
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.DELETE_COMMENT;
    }
}
