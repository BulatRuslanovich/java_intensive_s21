package edu.school21.sockets.client;

import edu.school21.sockets.json.JSONConverter;
import edu.school21.sockets.json.JSONMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class ServerReader extends Thread {

    private final Scanner reader;
    private final Socket socket;
    private final PrintWriter writer;
    private final ServerWriter serverWriter;

    public ServerReader(Scanner reader, PrintWriter writer, Socket socket, ServerWriter serverWriter) {
        this.reader = reader;
        this.writer = writer;
        this.socket = socket;
        this.serverWriter = serverWriter;
    }

    @Override
    public void run() {
        try {
            receiveMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void receiveMessage() throws IOException {
        while (!Thread.currentThread().isInterrupted() && reader.hasNextLine()) {
            String getJSON = reader.nextLine();
            JSONMessage jsonMessage = JSONConverter.parseToObject(getJSON);
            String message = jsonMessage.getMessageText();

            if ("Enter username: ".equals(message)) {
                serverWriter.setReadingThree(false);
            }

            if ("Choose command:".equals(message)) {
                serverWriter.setCanFinish(true);
            }

            if ("1. Create room".equals(message)) {
                serverWriter.setCanFinish(false);
            }

            if ("Authorization failed!".equals(message) || "Authorization successful!".equals(message) ||
                    message.contains("already exist") || "1. Create room".equals(message) || message.contains("created!")) {
                serverWriter.setReadingThree(true);
            }

            if ("You have left the chat".equals(message)) {
                serverWriter.setInRoom(false);
                serverWriter.setCanFinish(false);
            }

            if (message.contains("Rooms:") || message.contains("---")) {
                serverWriter.setReadingThree(true);
                serverWriter.setCanFinish(false);
            }

            if (message.contains("---")) {
                serverWriter.setInRoom(false);
                serverWriter.setCanFinish(false);
            }

            System.out.println(message);
        }

        if (serverWriter.isActive()) {
            serverWriter.setActive(false);
            Client.close(writer, reader, socket, -1);
        }
    }

}
