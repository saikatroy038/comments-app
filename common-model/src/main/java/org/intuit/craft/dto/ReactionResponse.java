package org.intuit.craft.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.intuit.craft.model.Reaction;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ReactionResponse {

    private String userId;

    private String name;

    private Reaction reaction;

    public ReactionResponse(String userId, String name, Reaction reaction) {
        this.userId = userId;
        this.name = name;
        this.reaction = reaction;
    }
}
