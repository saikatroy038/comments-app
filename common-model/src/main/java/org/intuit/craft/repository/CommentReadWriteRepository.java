package org.intuit.craft.repository;

import org.intuit.craft.entity.CommentEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@ConditionalOnProperty(name="write.enabled", havingValue="true")
public interface CommentReadWriteRepository extends CrudRepository<CommentEntity, UUID> {

    @Modifying
    @Query("UPDATE CommentEntity c SET c.content = :content, c.updatedAt = :updatedAt WHERE c.id = :commentId")
    void updateContentAndUpdatedAt(@Param("commentId") UUID commentId,
                                   @Param("content") String content,
                                   @Param("updatedAt") LocalDateTime updatedAt);
}
