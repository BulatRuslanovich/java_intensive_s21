package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        int port = parsePort(args);
        if (port == -1) {
            System.exit(-1);
        }

        try {
            Client client = new Client("127.0.0.1", port);
            client.start();
        } catch (IOException | RuntimeException e) {
            LOGGER.log(Level.SEVERE, String.format("Failed to start the client: %s", e.getMessage()), e);
            System.exit(-2);
        }
    }

    private static int parsePort(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            LOGGER.severe("Usage: --server-port=<port>");
            return -1;
        }

        try {
            int port = Integer.parseInt(args[0].substring(14));
            if (port <= 0 || port > 65535) {
                LOGGER.severe("Port number must be between 1 and 65535.");
                return -1;
            }
            return port;
        } catch (NumberFormatException e) {
            LOGGER.severe("Invalid port number: " + args[0].substring(14));
            return -1;
        }
    }
}
