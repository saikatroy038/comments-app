package org.intuit.craft.processor.listener;

import com.rabbitmq.client.Channel;
import org.intuit.craft.model.event.Comment;
import org.intuit.craft.model.event.Event;
import org.intuit.craft.model.event.EventType;
import org.intuit.craft.model.event.Reaction;
import org.intuit.craft.processor.worker.Worker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventListener {

    @Autowired
    private Map<EventType, Worker> eventWorkers;

    @RabbitListener(queues = "#{messagingConfig.commentsRoutingKey}", ackMode = "MANUAL", concurrency = "10")
    public void processComments(@Payload Event<Comment> event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            eventWorkers.get(event.getType()).process(event);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            System.out.println(String.format("Error, message cannot be processed: %s", e.getMessage()));
        }
    }

    @RabbitListener(queues = "#{messagingConfig.likesRoutingKey}", ackMode = "MANUAL", concurrency = "10")
    public void processReactions(@Payload Event<Reaction> event, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            eventWorkers.get(event.getType()).process(event);
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            System.out.println(String.format("Error, message cannot be processed: %s", e.getMessage()));
        }
    }
}

