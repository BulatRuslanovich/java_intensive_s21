package edu.school21.sockets.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
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
        receiveMessage();
        closeResources();
    }

    private void receiveMessage() {
        while (!Thread.currentThread().isInterrupted() && reader.hasNextLine() && serverWriter.active) {
            String message = reader.nextLine();
            System.out.println(message);
        }
    }

    private void closeResources() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
