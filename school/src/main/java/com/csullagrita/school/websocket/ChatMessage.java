package com.csullagrita.school.websocket;

import lombok.Data;

@Data
public class ChatMessage {
    private String senderName;
    private long courseId;
    private String text;
}
