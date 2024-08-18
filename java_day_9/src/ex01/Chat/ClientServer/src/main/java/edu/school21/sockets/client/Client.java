package edu.school21.sockets.client;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private final Socket socket;
    private final PrintWriter writer;
    private final Scanner reader;

    public Client(String ip, int port) throws IOException {
        socket = SocketFactory.getDefault().createSocket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void start() {
        try {
            ServerWriter serverWriter = new ServerWriter(writer, reader, socket);
            ServerReader serverReader = new ServerReader(reader, writer, socket, serverWriter);
            serverReader.start();
            serverWriter.start();

            serverReader.join();
            serverWriter.join();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Client operation interrupted", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error occurred during client operation", e);
        } finally {
            try {
                closeResources();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Failed to close resources", e);
            }
        }
    }

    private void closeResources() throws IOException {
        if (reader != null) {
            reader.close();
        }
        if (writer != null) {
            writer.close();
        }
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }

        LOGGER.info("Client resources closed successfully.");
    }

    public static void close(PrintWriter writer, Scanner reader, Socket socket, int flag) {
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
            LOGGER.log(Level.SEVERE, "Error closing resources", e);
        }

        if (flag == -1) {
            LOGGER.warning("Server is DEAD :(");
        }
    }
}
