package com.example.JavaChatApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */

@Service
public class WebSocketSessionManager {
    private final ArrayList<String> activeUserList = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketSessionManager(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void addUsername(String username){
        activeUserList.add(username);
    }

    public void removeUsername(String username){
        activeUserList.remove(username);
    }

    public void broadcastActiveUsername() {
        messagingTemplate.convertAndSend("/topic/users", activeUserList);
        System.out.println("Broadcasting active users to /topic/users " + activeUserList);
    }
}
