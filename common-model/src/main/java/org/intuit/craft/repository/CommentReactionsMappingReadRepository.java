package org.intuit.craft.repository;

import org.intuit.craft.dto.ReactionResponse;
import org.intuit.craft.entity.CommentReactionsMappingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentReactionsMappingReadRepository extends PagingAndSortingRepository<CommentReactionsMappingEntity, UUID> {

        @Query("SELECT NEW org.intuit.craft.dto.ReactionResponse(u.userId, u.name, rc.reaction) " +
                "FROM CommentReactionsMappingEntity rc " +
                "JOIN UserEntity u ON rc.userId = u.userId " +
                "WHERE rc.commentId = :commentId AND rc.reaction != 'NONE'")
        Page<ReactionResponse> getReactions(@Param("commentId") UUID commentId, Pageable pageable);
}
