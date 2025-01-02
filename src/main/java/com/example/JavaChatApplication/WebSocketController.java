package com.example.JavaChatApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final WebSocketSessionManager sessionManager;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate, WebSocketSessionManager sessionManager) {
        this.messagingTemplate = messagingTemplate;
        this.sessionManager = sessionManager;
    }

    @MessageMapping("/message")
    public void handleMessage(Message message){
        System.out.println("Received Message form user: " + message.getUser() + " : " + message.getMessage());
        messagingTemplate.convertAndSend("/topic/messages", message);
        System.out.println("Sent Message to /topic/messages: " + message.getUser() + " : " + message.getMessage());
    }

    @MessageMapping("/connect")
    public void connectUser(String username){
        sessionManager.addUsername(username);
        System.out.println(username + " connected.");
        sessionManager.broadcastActiveUsername();
    }

    @MessageMapping("/disconnect")
    public void disconnectUser(String username){
        sessionManager.removeUsername(username);
        System.out.println(username + " disconnected.");
        sessionManager.broadcastActiveUsername();
    }

    @MessageMapping("/request-users")
    public void requestUsers(){
        sessionManager.broadcastActiveUsername();
        System.out.println("Requesting Users.");
    }
}
