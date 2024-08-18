package edu.school21.sockets.server;

import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Slf4j
@Component
public class Server {
    private final UserService userService;
    private ServerSocket serverSocket;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private int num = 0;

    public Server(UserService userService) {
        this.userService = userService;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            log.info("Server started on port {}", port);

            new CommandListener().start();

            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                clients.add(client);
                log.info("New client connected! Number of clients: {}", ++num);
                client.start();
            }
        } catch (IOException e) {
            log.error("Server error: {}", e.getMessage(), e);
            stop();
        }
    }

    private class CommandListener extends Thread {
        private volatile boolean running = true;

        @Override
        public void run() {
            try (Scanner scanner = new Scanner(System.in)) {
                while (running && scanner.hasNextLine()) {
                    String command = scanner.nextLine().trim();
                    if ("exit".equalsIgnoreCase(command)) {
                        stopServer();
                    }
                }
            } catch (Exception e) {
                log.error("Error in command listener: {}", e.getMessage(), e);
            }
        }

        private void stopServer() {
            running = false;
            interrupt();
            Server.this.stop();
        }
    }

    private void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            log.error("Error closing server socket: {}", e.getMessage(), e);
        }
        System.exit(0);
    }

    private class ClientHandler extends Thread {
        private final PrintWriter writer;
        private final Scanner reader;
        private final Socket socket;
        private String username;
        private String password;
        @Getter
        private boolean active;
        private volatile boolean running = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
            PrintWriter tempWriter = null;
            Scanner tempReader = null;
            try {
                tempWriter = new PrintWriter(socket.getOutputStream(), true);
                tempReader = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                log.error("Error initializing client I/O: {}", e.getMessage(), e);
            }
            this.writer = tempWriter;
            this.reader = tempReader;
        }

        @Override
        public void run() {
            try {
                writer.println("Hello from Server!");
                writer.println("Choose command: {signUp, signIn, exit}");

                while (running && reader.hasNextLine()) {
                    String command = reader.nextLine().trim();

                    switch (command.toLowerCase()) {
                        case "signup":
                            handleSignUp();
                            break;
                        case "signin":
                            if (handleSignIn()) {
                                writer.println("Start messaging");
                                talk();
                                break;
                            }
                            break;
                        case "exit":
                            exitChat();
                            return;
                        default:
                            writer.println("Unknown command!");
                            break;
                    }
                }
            } catch (IOException e) {
                log.error("Client handling error: {}", e.getMessage(), e);
            } finally {
                exitChat();
            }
        }

        private void handleSignUp() throws IOException {
            if (getUserCredentials()) {
                userService.signUp(new User(null, username, password));
                writer.println("User: " + username + " created!");
                log.info("User signed up: {}", username);
            } else {
                writer.println("Sign-up cancelled.");
            }
        }

        private boolean handleSignIn() throws IOException {
            if (getUserCredentials() && userService.signIn(username, password)) {
                writer.println("Authorization successful!");
                log.info("Authorization successful for user: {}", username);
                return true;
            }
            writer.println("Authorization failed!");
            log.warn("Authorization failed for user: {}", username);
            return false;
        }

        private boolean getUserCredentials() {
            writer.println("Enter username: ");
            username = reader.nextLine().trim();
            if ("exit".equalsIgnoreCase(username)) {
                return false;
            }

            writer.println("Enter password: ");
            password = reader.nextLine().trim();
            return !"exit".equalsIgnoreCase(password);
        }

        private void talk() {
            active = true;
            while (true) {
                String message = reader.nextLine().trim();
                if ("exit".equalsIgnoreCase(message)) {
                    exitChat();
                    break;
                }
                sendMessageToAll(username + ": " + message);
            }
        }

        private void sendMessageToAll(String message) {
            userService.createMessage(message);
            synchronized (clients) {
                clients.stream()
                        .filter(ClientHandler::isActive)
                        .filter(c -> !c.username.equals(this.username))
                        .forEach(c -> c.sendMessage(message));
            }
        }



        private synchronized void exitChat() {
            if (running) {
                running = false;
                try {
                    writer.println("exit");
                    removeClient(this);
                    if (reader != null) {
                        reader.close();
                    }

                    writer.close();

                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    log.error("Error closing client connection: {}", e.getMessage(), e);
                }
            }
        }

        private void removeClient(ClientHandler client) {
            clients.remove(client);
            num--;
            log.info("The user has left the chat.");
        }

        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}

