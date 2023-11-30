package com.learnKafka.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.learnKafka.constants.KafkaConstants;

@Component
public class MessageListener {
	@Autowired
	public SimpMessagingTemplate template;
	
	@KafkaListener(topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID)
	public void listen(Message message) {
		System.out.println("Kafka Listening!");
		template.convertAndSend("/topic/group", message);
	}
	
	
}
