package org.intuit.craft.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intuit.craft.model.Reaction;
import org.intuit.craft.model.ReactionStatus;

import java.util.UUID;

@Entity
@Table(name = "comments_reactions_mappings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentReactionsMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Reaction reaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Reaction prevReaction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionStatus status;

    @Column(nullable = false)
    private UUID commentId;

    @Column(nullable = false)
    private String userId;
}
