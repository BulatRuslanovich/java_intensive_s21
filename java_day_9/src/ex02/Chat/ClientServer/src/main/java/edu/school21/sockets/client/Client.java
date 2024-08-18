package edu.school21.sockets.client;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter writer;
    private final Scanner reader;

    public Client(String ip, int port) throws IOException {
        socket = SocketFactory.getDefault().createSocket(ip, port);
        reader = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public static synchronized void close(PrintWriter writer, Scanner reader, Socket socket, int flag) throws IOException {
        writer.close();
        socket.close();
        reader.close();

        if (flag == -1) {
            System.out.println("Server connection lost :(");
            return;
        }
        System.exit(flag);
    }

    public void start() throws InterruptedException {
        ServerWriter serverWriter = new ServerWriter(writer, reader, socket);
        ServerReader serverReader = new ServerReader(reader, writer, socket, serverWriter);
        serverReader.start();
        serverWriter.start();

        serverReader.join();
        serverWriter.join();
    }
}
