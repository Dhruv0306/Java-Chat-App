package com.example.JavaChatApplication.client;

import com.example.JavaChatApplication.Message;

import java.util.ArrayList;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public interface MessageListener {
    void onMessageReceive(Message message);
    void onActiveUsersUpdated(ArrayList<String> users);
}
