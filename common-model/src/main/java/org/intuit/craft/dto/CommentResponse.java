package org.intuit.craft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intuit.craft.model.CommentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class CommentResponse {

    private UUID id;

    private String content;

    private LocalDateTime updatedAt;

    private CommentStatus status;

    private Boolean hasReplies;

    private String userId;

    private String name;

    private Long likes;

    private Long disLikes;

    public CommentResponse(UUID id, String content, LocalDateTime updatedAt, CommentStatus status, Boolean hasReplies,
                           String userId, String name, Long likes, Long disLikes) {
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.status = status;
        this.hasReplies = hasReplies;
        this.userId = userId;
        this.name = name;
        this.likes = likes;
        this.disLikes = disLikes;
    }

    public void hideDeleted() {
        if (CommentStatus.DELETED.equals(status)) {
            content = null;
        }
    }
}
