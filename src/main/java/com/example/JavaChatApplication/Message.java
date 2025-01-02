package com.example.JavaChatApplication;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public class Message {
    private String User;
    private String Message;

    public Message() {}

    public Message(String user, String message) {
        this.setUser(user);
        this.setMessage(message);
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
