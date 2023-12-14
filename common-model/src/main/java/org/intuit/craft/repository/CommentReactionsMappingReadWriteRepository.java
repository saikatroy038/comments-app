package org.intuit.craft.repository;

import jakarta.persistence.LockModeType;
import org.intuit.craft.entity.CommentReactionsMappingEntity;
import org.intuit.craft.model.ReactionStatus;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name="write.enabled", havingValue="true")
public interface CommentReactionsMappingReadWriteRepository extends CrudRepository<CommentReactionsMappingEntity, UUID> {

    Optional<CommentReactionsMappingEntity> findByCommentIdAndUserId(UUID commentId, String userId);

    @Query("SELECT crm FROM CommentReactionsMappingEntity crm WHERE crm.status = :status ORDER BY crm.id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<CommentReactionsMappingEntity> findAndUpdateNewStatusWithLock(
            @Param("status") ReactionStatus status, Pageable pageable);
}
