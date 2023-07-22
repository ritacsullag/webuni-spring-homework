package com.csullagrita.school.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    //mas neveben ne lehessen uzit kuldeni
    @MessageMapping("/chat")
    @PreAuthorize("#message.sender == authentication.principal.username") /*authentication: authenticationManager visszaad, Principal: altalunk gyartott userDetails*/
    public void send(ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/courseChat/" + chatMessage.getCourseId(),
                String.format("%s: %s", chatMessage.getSenderName(), chatMessage.getText()));
    }
}
