# Java Chat Application

## Overview
This project is a real-time Java-based chat application built using Spring Boot and WebSocket technology. It provides a responsive graphical user interface (GUI) for seamless communication between users, featuring user management, message broadcasting, and active user tracking.

## Features
- **Real-time Messaging**: Exchange messages instantly using WebSocket.
- **User-Friendly GUI**: Built using Swing for intuitive user interaction.
- **Active User List**: View all connected users in real time.
- **Dynamic Message Layout**: Automatically adjusts to the window size for better readability.
- **User Connection Management**: Connect, disconnect, and notify users dynamically.

## Directory Structure
```
Dhruv0306-Java-Chat-App/
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── com/
    │   │       └── example/
    │   │           └── JavaChatApplication/
    │   │               ├── JavaChatApplication.java
    │   │               ├── Message.java
    │   │               ├── WebSocketController.java
    │   │               ├── WebSocketSessionManager.java
    │   │               ├── WebsocketConfig.java
    │   │               └── client/
    │   │                   ├── App.java
    │   │                   ├── ClientGUI.java
    │   │                   ├── MessageListener.java
    │   │                   ├── MyStompClient.java
    │   │                   ├── MyStompSessionHandler.java
    │   │                   └── Utilities.java
    │   └── resources/
    │       └── application.properties
    └── test/
        └── java/
            └── com/
                └── example/
                    └── JavaChatApplication/
                        └── JavaChatApplicationTests.java
```

## Prerequisites
- Java Development Kit (JDK) 21 or later
- Maven 3.8+
- Spring Boot 3.4.0

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/Dhruv0306/JavaChatApp.git
   cd Dhruv0306-Java-Chat-App
   ```

2. Build the application using Maven:
   ```bash
   ./mvnw clean install
   ```

3. Run the Spring Boot server:
   ```bash
   ./mvnw spring-boot:run
   ```

4. Start the client application:
   ```bash
   java -jar target/JavaChatApplication-0.0.1-SNAPSHOT.jar
   ```

5. Enter a username when prompted to join the chat.

## Key Components
### Backend
1. **WebSocketController**: Handles WebSocket endpoints for messaging and user management.
2. **WebSocketSessionManager**: Manages active user sessions and broadcasts updates.
3. **WebsocketConfig**: Configures WebSocket endpoints and message brokers.

### Frontend (Client GUI)
1. **ClientGUI**: Provides the main graphical interface for users.
2. **MyStompClient**: Manages WebSocket connections for the client.
3. **Utilities**: Helper functions for GUI styling and layout adjustments.

## Configuration
Modify `src/main/resources/application.properties` to set application-specific properties. Default WebSocket endpoint is configured as:
```
spring.application.name=JavaChatApplication
```

## Testing
Run unit tests using:
```bash
./mvnw test
```
Tests are located in `src/test/java/com/example/JavaChatApplication/`.

## Contributing
Feel free to fork the repository and submit pull requests. Contributions are welcome to improve functionality, fix bugs, or enhance the user experience.

## License
This project is licensed under the Apache 2.0 License. See the LICENSE file for details.

## Author
**Dhruv Patel**
- GitHub: [Dhruv0306](https://github.com/Dhruv0306)

Enjoy chatting!

