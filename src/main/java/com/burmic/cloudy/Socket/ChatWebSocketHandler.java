package com.burmic.cloudy.Socket;

import com.burmic.cloudy.Entities.Message;
import com.burmic.cloudy.Services.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final MessageService chatService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public ChatWebSocketHandler(MessageService chatService){
        this.chatService = chatService;
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("new connection : "+ session.getId());
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received: " + payload);

        try {
            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
            Long userId = Long.valueOf(data.get("userId").toString());
            Long receiverId = Long.valueOf(data.get("receiverId").toString());
            String text = data.get("text").toString();

            if (!sessions.containsKey(userId)) {
                sessions.put(userId, session);
                System.out.println("Registered user " + userId + " with session " + session.getId());
            }

            Message savedMessage = chatService.sendMessage(text, userId,receiverId);
            String response = objectMapper.writeValueAsString(savedMessage);

            // Send to receiver
            WebSocketSession receiverSession = sessions.get(receiverId);
            if (receiverSession != null && receiverSession.isOpen()) {
                System.out.println("Sending to receiver " + receiverId + ": " + response);
                receiverSession.sendMessage(new TextMessage(response));
            } else {
                System.out.println("Receiver " + receiverId + " not connected or session closed");
            }

            // Send to sender
            if (session.isOpen()) {
                System.out.println("Sending to sender " + userId + ": " + response);
                session.sendMessage(new TextMessage(response));
            } else {
                System.out.println("Sender session " + userId + " is closed");
            }
        } catch (IOException e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.values().remove(session);
        System.out.println("Disconnected : " + session.getId());
    }
}
