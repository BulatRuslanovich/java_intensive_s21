package edu.school21.sockets.app;

import edu.school21.sockets.server.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--port=")) {
            log.error("Usage: --port=<port>");
            System.exit(-1);
        }

        try {
            int port = parsePort(args[0]);
            ApplicationContext context = new AnnotationConfigApplicationContext("edu.school21.sockets");
            Server server = context.getBean(Server.class);
            server.start(port);
        } catch (RuntimeException e) {
            log.error("An error occurred while starting the server: {}", e.getMessage(), e);
            System.exit(-2);
        }
    }

    private static int parsePort(String arg) {
        try {
            int port = Integer.parseInt(arg.substring(7));
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("Port number must be between 1 and 65535.");
            }
            return port;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number: " + arg.substring(7));
        }
    }
}
