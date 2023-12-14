package org.intuit.craft.processor.worker;

import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;

public interface Worker<E> {

    void process(Event<E> event);

    EventType getEventType();
}
