package edu.school21.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedReader input;
    private PrintWriter out;

    public Client(int port) {
        try {
            clientSocket = new Socket("localhost", port);
            reader = new BufferedReader(new InputStreamReader(System.in));
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    public void start() {
        try {
            handleServerCommunication();
        } catch (IOException e) {
            System.err.println("Communication error: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void handleServerCommunication() throws IOException {
        String serverMsg = readServerMessage();
        promptUser(serverMsg);

        String signUpCommand = reader.readLine();
        sendMessageToServer(signUpCommand);

        while (!"signup".equalsIgnoreCase(signUpCommand)) {
            promptUser(readServerMessage());
            signUpCommand = reader.readLine();
            sendMessageToServer(signUpCommand);
        }

        promptUser(readServerMessage());
        String userName = reader.readLine();
        sendMessageToServer(userName);

        promptUser(readServerMessage());
        String password = reader.readLine();
        sendMessageToServer(password);

        System.out.println(readServerMessage());
    }

    private String readServerMessage() throws IOException {
        return input.readLine();
    }

    private void sendMessageToServer(String message) {
        out.println(message);
    }

    private void promptUser(String message) {
        System.out.print(message + "\n> ");
    }

    private void closeResources() {
        try {
            if (clientSocket != null) {
                clientSocket.close();
            }
            if (input != null) {
                input.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
