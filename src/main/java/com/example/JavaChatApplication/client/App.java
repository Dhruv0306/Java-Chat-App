package com.example.JavaChatApplication.client;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String username = JOptionPane.showInputDialog(null,
                    "Enter Username: (Max: 16 Character)",
                    "Chat Application",
                    JOptionPane.QUESTION_MESSAGE);

            if (username == null || username.isEmpty() || username.length() > 16){
                JOptionPane.showMessageDialog(null,
                        "Invalid Username.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            ClientGUI clientGUI = null;
            try {
                clientGUI = new ClientGUI(username);
            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            clientGUI.setVisible(true);
        });
    }
}
