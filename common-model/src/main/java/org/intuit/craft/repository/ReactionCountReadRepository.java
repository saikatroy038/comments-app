package org.intuit.craft.repository;

import org.intuit.craft.entity.ReactionCountEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReactionCountReadRepository extends PagingAndSortingRepository<ReactionCountEntity, UUID> {
}
