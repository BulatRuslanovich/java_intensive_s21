package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;


public class Main {
    private static int port;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            port = parsePort(args);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

        while (true) {
            newConnection();
            System.out.println("To reconnect, enter \"start\" or \"exit\" for finish program");
            String answer = scanner.nextLine();
            while (!answer.equalsIgnoreCase("start")) {
                if (answer.equalsIgnoreCase("exit")) {
                    scanner.close();
                    System.exit(0);
                }
                answer = scanner.nextLine();

            }
        }
    }

    public static void newConnection() {
        try {
            Client client = new Client("127.0.0.1", port);
            client.start();
        } catch (Exception e) {
            System.err.println("Failed to connect");
        }
    }

    private static int parsePort(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--server-port=")) {
            System.err.println("Specify the server port using --server-port=<port>");
            System.exit(-1);
        }



        try {
            int port = Integer.parseInt(args[0].substring(14));
            if (port <= 0 || port > 65535) {
                throw new IllegalArgumentException("Port number must be between 1 and 65535.");
            }
            return port;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port number: " + args[0].substring(14));
        }
    }
}
