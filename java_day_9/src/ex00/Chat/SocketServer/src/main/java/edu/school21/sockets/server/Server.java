package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private final int port;
    private final AnnotationConfigApplicationContext ctx;
    private final UsersService service;

    public Server(int port) {
        this.port = port;
        this.ctx = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);

        ctx.register(SocketsApplicationConfig.class);

        for (String beanName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanName);
        }
        System.out.println();

        this.service = ctx.getBean(UsersService.class);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port);
             Socket socket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            writer.println("Hello from server!");

            handleCommands(reader, writer);

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (ctx != null) {
                ctx.close();
            }
        }
    }

    private void handleCommands(BufferedReader reader, PrintWriter writer) throws IOException {
        String regisCommand = reader.readLine();
        while (!"signup".equalsIgnoreCase(regisCommand)) {
            writer.println("Enter 'signup' to register:");
            regisCommand = reader.readLine();
        }

        writer.println("Enter username:");
        String username = reader.readLine();

        writer.println("Enter password:");
        String password = reader.readLine();

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        if (service.signUp(user)) {
            writer.println("Registration successful!");
        } else {
            writer.println("User with this username already exists.");
        }
    }
}
