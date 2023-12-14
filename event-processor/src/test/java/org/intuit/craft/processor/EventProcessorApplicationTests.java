package org.intuit.craft.processor;

import org.intuit.craft.processor.listener.EventListener;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class EventProcessorApplicationTests {

	@MockBean
	EventListener eventListener;

	@Test
	void contextLoads() {
	}

}
