package org.intuit.craft.processor.worker;

import org.intuit.craft.entity.CommentEntity;
import org.intuit.craft.model.event.Comment;
import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.processor.mapper.CommentMapper;
import org.intuit.craft.repository.CommentReadWriteRepository;
import org.intuit.craft.util.LocalDateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddCommentWorker implements Worker<Comment> {

    @Autowired
    private CommentReadWriteRepository commentReadWriteRepository;

    @Transactional
    @Override
    public void process(Event<Comment> event) {
        if (event.getData().getParentId() == null
                || commentReadWriteRepository.existsById(event.getData().getParentId())) {
            CommentEntity commentEntity = CommentMapper.getCommentEntityFromComment(event.getData());
            commentEntity.setId(null);
            commentEntity.setCreatedAt(LocalDateTimeUtil.getLocalDateTimeFromString(event.getCreatedAt()));
            commentEntity.setUpdatedAt(LocalDateTimeUtil.getLocalDateTimeFromString(event.getCreatedAt()));
            commentReadWriteRepository.save(commentEntity);

            if (event.getData().getParentId() != null) {
                CommentEntity parentComment = commentReadWriteRepository.findById(event.getData().getParentId()).get();
                parentComment.setHasReplies(true);
                commentReadWriteRepository.save(parentComment);
            }
        }
    }

    @Override
    public EventType getEventType() {
        return EventType.ADD_COMMENT;
    }
}
