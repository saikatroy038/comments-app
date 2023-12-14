package org.intuit.craft.post.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intuit.craft.model.Reaction;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionRequest {

    @NotNull(message = "commentId cannot be null")
    private UUID commentId;

    @NotNull(message = "reaction cannot be null")
    private Reaction reaction;
}
