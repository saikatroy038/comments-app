package org.intuit.craft.processor.config;

import org.intuit.craft.model.event.EventType;
import org.intuit.craft.processor.worker.Worker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class WorkerConfig {

    @Bean
    public Map<EventType, Worker> eventWorkers(List<Worker> workers) {
        Map<EventType, Worker> eventToWorker = new HashMap<>();
        for (Worker worker : workers) {
            eventToWorker.put(worker.getEventType(), worker);
        }
        return eventToWorker;
    }
}
