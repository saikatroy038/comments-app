package org.intuit.craft.repository;

import jakarta.persistence.LockModeType;
import org.intuit.craft.entity.ReactionCountEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name="write.enabled", havingValue="true")
public interface ReactionCountReadWriteRepository extends CrudRepository<ReactionCountEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT rc FROM ReactionCountEntity rc WHERE rc.commentId IN :commentIds")
    List<ReactionCountEntity> findEntriesWithCommentIdsAndLockTable(@Param("commentIds") Set<UUID> commentIds);
}
