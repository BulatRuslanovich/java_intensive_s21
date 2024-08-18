package edu.school21.sockets.client;

import edu.school21.sockets.json.JSONConverter;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ServerWriter extends Thread {
    private final PrintWriter writer;
    private final Scanner scanner = new Scanner(System.in);
    private final Scanner reader;
    private final Socket socket;
    @Getter
    @Setter
    private boolean active = true;
    @Getter
    @Setter
    private boolean isReadingThree = true;
    @Getter
    @Setter
    private boolean inRoom = false;
    @Getter
    @Setter
    private boolean canFinish = true;

    public ServerWriter(PrintWriter writer, Scanner reader, Socket socket) {
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

    private void sendMessage() throws IOException {
        while (active) {
            String toSendMessage = scanner.nextLine();
            JSONObject messageJSON = JSONConverter.makeJSONObject(toSendMessage);

            String message = messageJSON.toJSONString();

            if (("exit".equals(toSendMessage) && !inRoom && canFinish) ||
                    ("3".equals(toSendMessage) && isReadingThree && canFinish)) {
                active = false;
                Client.close(writer, reader, socket, 0);
            }
            writer.println(message);
        }
    }
}
