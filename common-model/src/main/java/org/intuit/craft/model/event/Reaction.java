package org.intuit.craft.model.event;

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
public class Reaction {

    private String userId;

    private UUID commentId;

    private org.intuit.craft.model.Reaction reaction;
}
