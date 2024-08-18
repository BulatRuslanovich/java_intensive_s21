package edu.school21.sockets.server;

import edu.school21.sockets.json.JSONConverter;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.RoomRepository;
import edu.school21.sockets.repositories.UserRepository;
import edu.school21.sockets.services.RoomService;
import edu.school21.sockets.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Slf4j
@Component
public class Server {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;


    private final UserService userService;
    private final RoomService roomService;
    private ServerSocket serverSocket;
    private final Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());
    private final Set<String> rooms = Collections.synchronizedSet(new HashSet<>());

    public Server(UserRepository userRepository, RoomRepository roomRepository, UserService userService, RoomService roomService) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.userService = userService;
        this.roomService = roomService;
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
                log.info("New client connected! Number of clients: {}", clients.size());
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
        private String roomTitle;
        private String password;

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
            sendMessage("Hello from Server!");

            while (running) {
                sendMessage("Choose command:");
                sendMessage("1. SignUp");
                sendMessage("2. SignIn");
                sendMessage("3. Exit");

                try {
                    if (reader.hasNextLine()) {
                        String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();

                        if ("1".equalsIgnoreCase(message)) {
                            handleSignUp();
                        } else if ("2".equalsIgnoreCase(message)) {
                            if (handleSignIn()) {
                                chooseOrCreateRoom();
                            }
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            exitChat();
                            return;
                        } else {
                            sendMessage("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    sendMessage(e.getMessage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void chooseOrCreateRoom() {
            while(true) {
                sendMessage("1. Create room");
                sendMessage("2. Choose room");
                sendMessage("3. Exit");
                try {
                    if (reader.hasNextLine()) {
                        String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();

                        if ("1".equalsIgnoreCase(message)) {
                            createRoom();
                            talk();
                        } else if ("2".equalsIgnoreCase(message)) {
                            chooseRoom();
                        } else if ("3".equals(message) || "exit".equals(message)) {
                            log.info("User: {} logged out", username);
                            sendMessage("Logout for " + username);
                            return;
                        } else {
                            sendMessage("Unknown command!");
                        }
                    } else {
                        exitChat();
                        return;
                    }
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    sendMessage(e.getMessage());
                }
            }
        }

        private void chooseRoom() {
            int numberRoom;
            List<Chatroom> allRooms = roomService.showAllRooms();
            if (allRooms.isEmpty()) {
                sendMessage("No room created!");
                return;
            } else {
                sendMessage("Rooms: ");

                for (int i = 0; i < allRooms.size(); i++) {
                    sendMessage(i + 1 + ". " + allRooms.get(i).getTitle());
                }

                sendMessage(allRooms.size() + 1 + ". Exit");
            }
            while (true) {
                try {
                    numberRoom = Integer.parseInt(JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText());
                    if (numberRoom <= allRooms.size() && numberRoom > 0) {
                        roomTitle = allRooms.get(numberRoom - 1).getTitle();
                        log.info("User {} in room: {}", username, roomTitle);
                        talk();
                        break;
                    } else if (numberRoom == allRooms.size() + 1) {
                        return;
                    } else {
                        sendMessage("Wrong number of room. Please, try again.");
                    }
                } catch (Exception e) {
                    sendMessage(e.getMessage());
                }
            }
        }

        private void createRoom() {
            sendMessage("The process of creating a room has begun...");
            sendMessage("Enter Title for new room");
            do {
                roomTitle = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();
            } while ("".equals(roomTitle));

            Optional<User> user = userRepository.findByUsername(username);

            if (!user.isPresent()) {
                return;
            }

            roomService.createRoom(Chatroom.builder()
                            .title(roomTitle)
                            .ownerId(user.get().getId())
                    .build());

            rooms.add(roomTitle);
            log.info("User {} created room {}", username, roomTitle);
            sendMessage(String.format("Room %s created!", roomTitle));
        }


        private void handleSignUp() throws IOException {
            if (getUserCredentials()) {
                userService.signUp(new User(null, username, password));
                sendMessage("User: " + username + " created!");
                log.info("User signed up: {}", username);
            } else {
                sendMessage("Sign-up cancelled.");
            }
        }

        private boolean handleSignIn() {
            if (!getUserCredentials()) {
                exitChat();
            }

            if (userService.signIn(username, password)) {
                sendMessage("Authorization successful!");
                log.info("Authorization successful for user: {}", username);

                Optional<User> userOpt = userRepository.findByUsername(username);
                if (userOpt.isPresent()) {
                    Optional<Message> messageOpt = userService.findLastRoom(userOpt.get().getId());
                    if (messageOpt.isPresent()) {
                        Optional<Chatroom> roomOpt = roomRepository.findById(messageOpt.get().getRoomId());
                        if (!roomOpt.isPresent()) return true;

                        String lastRoom = roomOpt.get().getTitle();
                        sendMessage("______________________________________________");
                        sendMessage("Last time you were in room: '" + lastRoom + "'");
                        showLastThirtyMessage(lastRoom);
                        sendMessage("______________________________________________");
                    }

                    return true;
                }
            }
            sendMessage("Authorization failed!");
            log.warn("Authorization failed for user: {}", username);
            return false;
        }

        private boolean getUserCredentials() {
            sendMessage("Enter username: ");
            username = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();

            if ("exit".equalsIgnoreCase(username)) {
                return false;
            }

            sendMessage("Enter password: ");

            password = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();
            return !"exit".equalsIgnoreCase(password);
        }

        private void talk() {
            sendMessage(roomTitle + " ---");
            showLastThirtyMessage(roomTitle);

            while (true) {
                String message = JSONConverter.parseToObject(reader.nextLine().trim()).getMessageText();
                if ("exit".equalsIgnoreCase(message)) {
                    sendMessage("You have left the chat");
                    break;
                }

                sendMessageToRoom(message, roomTitle, username);
            }
        }

        private void showLastThirtyMessage(String title) {
            Optional<Chatroom> roomOpt = roomRepository.findByTitle(title);

            if (!roomOpt.isPresent()) {
                return;
            }

            List<Message> allMessage = userService.getAllMessageByRoomId(roomOpt.get().getId());

            if (!allMessage.isEmpty()) {
                for (Message msg : allMessage) {
                    Optional<User> userOpt = userRepository.findById(msg.getAuthorId());

                    userOpt.ifPresent(user -> sendMessage(user.getUsername() + ": " + msg.getMessageText()));
                }
            }
        }

        private void sendMessageToRoom(String message, String roomTitle, String senderName) {
            Optional<User> optionalUser = userRepository.findByUsername(senderName);
            Optional<Chatroom> optionalChatroom = roomRepository.findByTitle(roomTitle);

            if (optionalChatroom.isPresent() && optionalUser.isPresent()) {
                User user = optionalUser.get();
                Chatroom chatroom = optionalChatroom.get();

                userService.createMessage(message, user.getId(), chatroom.getId());
                synchronized (clients) {
                    clients.stream()
                            .filter(c -> Objects.equals(c.roomTitle, this.roomTitle))
                            .forEach(c -> c.sendMessage(String.format("%s : %s", senderName, message)));
                }
            }
        }



        private synchronized void exitChat() {
            if (running) {
                running = false;
                try {
                    sendMessage("exit");
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
            log.info("The user has left the chat.");
        }

        public void sendMessage(String message) {
            writer.println(JSONConverter.makeJSONObject(message).toJSONString());
        }
    }
}

