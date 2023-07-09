package com.csullagrita.school.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void send(ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/courseChat/" + chatMessage.getCourseId(),
                String.format("%s: %s", chatMessage.getSenderName(), chatMessage.getText()));
    }
}
