package org.intuit.craft.repository;

import org.intuit.craft.dto.CommentResponse;
import org.intuit.craft.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentReadRepository extends PagingAndSortingRepository<CommentEntity, UUID> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CommentEntity c WHERE c.id = :commentId")
    boolean existsById(@Param("commentId") UUID commentId);

    @Query("SELECT NEW org.intuit.craft.dto.CommentResponse(c.id, c.content, c.updatedAt, c.status, c.hasReplies, u.userId, u.name, COALESCE(rc.likes, 0), COALESCE(rc.disLikes, 0)) " +
            "FROM CommentEntity c " +
            "JOIN UserEntity u ON c.userId = u.userId " +
            "LEFT JOIN ReactionCountEntity rc ON c.id = rc.commentId " +
            "WHERE c.parentId = :parentId")
    Page<CommentResponse> findCommentsByParentId(@Param("parentId") UUID parentId, Pageable pageable);

    @Query("SELECT NEW org.intuit.craft.dto.CommentResponse(c.id, c.content, c.updatedAt, c.status, c.hasReplies, u.userId, u.name, COALESCE(rc.likes, 0), COALESCE(rc.disLikes, 0)) " +
            "FROM CommentEntity c " +
            "JOIN UserEntity u ON c.userId = u.userId " +
            "LEFT JOIN ReactionCountEntity rc ON c.id = rc.commentId " +
            "WHERE c.parentId IS NULL")
    Page<CommentResponse> findFirstLevelComments(Pageable pageable);
}
