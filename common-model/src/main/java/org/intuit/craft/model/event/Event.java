package org.intuit.craft.model.event;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class Event<E> {

    private UUID id;

    private E data;

    private EventType type;

    private String createdAt;

    public Event() {
        this.id = UUID.randomUUID();
    }
}
