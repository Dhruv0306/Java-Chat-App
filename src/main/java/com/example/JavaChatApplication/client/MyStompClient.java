package com.example.JavaChatApplication.client;

import com.example.JavaChatApplication.Message;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public class MyStompClient {
    private StompSession session;
    private String username;

    public MyStompClient(MessageListener messageListener, String username) throws ExecutionException, InterruptedException {
        this.username = username;

        List<Transport> transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));

        SockJsClient sockJsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        CountDownLatch latch = new CountDownLatch(1); // Create a latch
        StompSessionHandler SessionHandler = new MyStompSessionHandler(messageListener, username, latch);
        String url = "ws://localhost:8080/ws"; // Use ws:// for WebSocket

        session = stompClient.connectAsync(url, SessionHandler).get();
        latch.await(); // Wait until afterConnected signals completion
    }

    public void sendMessage(Message message, CountDownLatch latch){
        try {
            session.send("/app/message", message);
            System.out.println("Message sent: " + message.getMessage());
            latch.countDown();
            latch.countDown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void disconnectUser(String username){
        session.send("/app/disconnect", username);
        System.out.println("Disconnecting user: " + username);
    }
}
