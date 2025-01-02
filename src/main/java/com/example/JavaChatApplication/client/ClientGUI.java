package com.example.JavaChatApplication.client;

import com.example.JavaChatApplication.Message;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @author Dhruv Patel
 * @version 1.0.0
 */
public class ClientGUI extends JFrame implements MessageListener{
    private final int width, height;
    private JPanel connectedUserPanel, messagePanel;
    private final MyStompClient myStompClient;
    private final String username;
    private JScrollPane messagePanelScrollPane;

    public ClientGUI(String userName) throws ExecutionException, InterruptedException {
        // Parent Constructor
        super("User: " + userName);

        this.username = userName;
        myStompClient = new MyStompClient(this, username);

        // Set JFrame width and height
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) (dimension.width * 0.65);
        height = (int) (dimension.height *0.63);

        // Add general properties to JFrame
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(ClientGUI.this, "Do you want to exit?",
                        "Exit", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION){
                    myStompClient.disconnectUser(username);
                    ClientGUI.this.dispose();
                }
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateMessageSize();
            }
        });

        getContentPane().setBackground(Utilities.PRIMARY_COLOR);
        addGuiComponents();
    }

    private void updateMessageSize() {
        for (int i = 0; i < messagePanel.getComponents().length; i++){
            Component component = messagePanel.getComponent(i);
            if (component instanceof  JPanel){
                JPanel chatMessage = (JPanel) component;
                if (chatMessage.getComponent(1) instanceof JLabel){
                    JLabel messageLabel = (JLabel) chatMessage.getComponent(1);
                    messageLabel.setText("<html>" +
                            "<body style='width:" + (0.78 * getWidth()) + "'px>" +
                            messageLabel.getText() +
                            "</body>" +
                            "</html>");
                }
            }
        }
    }

    private void addGuiComponents(){
        addConnectedUsersComponents();
        addChatComponents();
    }

    private void addConnectedUsersComponents() {
        connectedUserPanel = new JPanel();
        connectedUserPanel.setBorder(Utilities.addPadding(9, 9, 9, 9));
        connectedUserPanel.setLayout(new BoxLayout(connectedUserPanel, BoxLayout.Y_AXIS));
        connectedUserPanel.setBackground(Utilities.SECONDARY_COLOR);
        connectedUserPanel.setPreferredSize(new Dimension((int) (0.18*width), height));

        JLabel connectedUserLabel = new JLabel("Connected Users");
        connectedUserLabel.setFont(new Font("Inter", Font.BOLD, 18));
        connectedUserLabel.setForeground(Utilities.TEXT_COLOR);
        connectedUserPanel.add(connectedUserLabel);

        add(connectedUserPanel, BorderLayout.WEST);
    }

    private void addChatComponents(){
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBackground(Utilities.TRANSPARENT_COLOR);

        messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Utilities.TRANSPARENT_COLOR);

        messagePanelScrollPane = new JScrollPane(messagePanel);
        messagePanelScrollPane.setBackground(Utilities.TRANSPARENT_COLOR);
        messagePanelScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        messagePanelScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        messagePanelScrollPane.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                revalidate();
                repaint();
            }
        });

        chatPanel.add(messagePanelScrollPane, BorderLayout.CENTER);


        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(Utilities.addPadding(5, 5, 5, 5));
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(Utilities.TRANSPARENT_COLOR);

        JTextField inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER){
                    String input = inputField.getText();
                    if (input.isEmpty()) return;

                    inputField.setText("");

                    myStompClient.sendMessage(new Message(username, input), new CountDownLatch(2));
                }
            }
        });
        inputField.setBackground(Utilities.SECONDARY_COLOR);
        inputField.setForeground(Utilities.TEXT_COLOR);
        inputField.setBorder(Utilities.addPadding(0, 5, 0, 5));
        inputField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        inputField.setFont(new Font("Inter", Font.PLAIN, 14));
        inputField.setPreferredSize(new Dimension(inputPanel.getWidth(), (int) (height * 0.065)));
        inputPanel.add(inputField, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);


        add(chatPanel, BorderLayout.CENTER);
    }

    private JPanel createChatMessageComponent(Message message){
        JPanel chatMessage = new JPanel();
        chatMessage.setBackground(Utilities.TRANSPARENT_COLOR);
        chatMessage.setLayout(new BoxLayout(chatMessage, BoxLayout.Y_AXIS));
        chatMessage.setBorder(Utilities.addPadding(3, 3, 3, 3));

        JLabel usernameLabel = new JLabel(message.getUser());
        usernameLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        usernameLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(usernameLabel);

        JLabel messageLabel = new JLabel();
        messageLabel.setText("<html>" +
                        "<body style='width:" + (0.78 * getWidth()) + "'px>" +
                            message.getMessage() +
                        "</body>" +
                "</html>");
        messageLabel.setFont(new Font("Inter", Font.BOLD, 16));
        messageLabel.setForeground(Utilities.TEXT_COLOR);
        chatMessage.add(messageLabel);

        return chatMessage;
    }

    @Override
    public void onMessageReceive(Message message) {
        System.out.println("OnMessageReceive.");
        messagePanel.add(createChatMessageComponent(message));
        revalidate();
        repaint();

        messagePanelScrollPane.getVerticalScrollBar().setValue(Integer.MAX_VALUE);
    }

    @Override
    public void onActiveUsersUpdated(ArrayList<String> users) {
        if (connectedUserPanel.getComponents().length >= 2){
            connectedUserPanel.remove(1);
        }
        JPanel userListPanel = new JPanel();
        userListPanel.setBackground(Utilities.TRANSPARENT_COLOR);
        userListPanel.setLayout(new BoxLayout(userListPanel, BoxLayout.Y_AXIS));

        for (String user: users){
            JLabel username = new JLabel();
            username.setText(user);
            username.setBackground(Utilities.TRANSPARENT_COLOR);
            username.setForeground(Utilities.TEXT_COLOR);
            username.setFont(new Font("Inter", Font.PLAIN, 16));
            userListPanel.add(username);
        }

        connectedUserPanel.add(userListPanel);
        revalidate();
        repaint();
    }
}
