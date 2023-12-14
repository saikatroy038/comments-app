package org.intuit.craft.post.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest {

    private UUID id;

    @NotNull(message = "Content must not be empty")
    @Size(min = 1, max = 1000, message = "Content must have between 1 and 1000 characters")
    private String content;

    private UUID parentId;
}
