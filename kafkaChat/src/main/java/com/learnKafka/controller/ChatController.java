package com.learnKafka.controller;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnKafka.constants.KafkaConstants;
import com.learnKafka.messages.Message;
import com.learnKafka.messages.MessageListener;

@RestController
@CrossOrigin("http://localhost:3000/")
public class ChatController {
	@Autowired
	public MessageListener messageListener;
	@Autowired
	public KafkaTemplate<String, Message> kafkaTemplate;
	
	@PostMapping(value="/api/send", consumes = "application/json", produces = "application/json")
	public void sendMessage(@RequestBody Message message) {
		message.setTimestamp(LocalDate.now().toString());
		try {
			System.out.println("Hello" + message);
			kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
		}catch (InterruptedException | ExecutionException e){
			throw new RuntimeException(e);
		}
	}
	
//	@PostMapping("/messages")
//	public void sendMessageToKafka(@RequestBody String message) {
//        producerService.sendMessage(message);
//    }
	
    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload(required=false) Message message) {
        //Sending this message to all the subscribers
        return message;
    }
    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    public Message addUser(@Payload(required=false) Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add user in web socket session
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
