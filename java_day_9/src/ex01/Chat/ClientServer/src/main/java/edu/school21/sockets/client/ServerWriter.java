package edu.school21.sockets.client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerWriter extends Thread {
    private final PrintWriter writer;
    private final Scanner scanner = new Scanner(System.in);
    boolean active = true;
    private final Scanner reader;
    private final Socket socket;

    public ServerWriter(PrintWriter writer,  Scanner reader, Socket socket) {
        this.writer = writer;
        this.reader = reader;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            sendMessage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void sendMessage() {
        while (active) {
            String message = scanner.nextLine();
            writer.println(message);


            if ("exit".equals(message)) {
                active = false;
            }
        }

        Client.close(writer, reader, socket, 0);
    }
}
