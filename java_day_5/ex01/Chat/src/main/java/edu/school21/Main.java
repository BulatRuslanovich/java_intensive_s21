package edu.school21;

import edu.school21.chat.dao.MessageDAO;
import edu.school21.chat.dao.impl.MessageDAOImpl;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter message ID: ");

        if (!sc.hasNextLong()) {
            System.out.println("Not long!");
            System.exit(-1);
        }

        Long id = sc.nextLong();

        MessageDAO messageDAO = MessageDAOImpl.getINSTANCE();

        System.out.println(messageDAO.findById(id).orElse(null));
    }
}