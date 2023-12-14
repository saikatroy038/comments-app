package org.intuit.craft.post.ebs;

import org.intuit.craft.model.event.Event;
import org.intuit.craft.util.LocalDateTimeUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;

@Component
public class EventPublisher {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publish(String exchange, String routingKey, Event<?> event) {
        event.setCreatedAt(LocalDateTimeUtil.getStringFromLocalDateTime(LocalDateTime.now()));
        amqpTemplate.convertAndSend(exchange, routingKey, event);
    }
}
