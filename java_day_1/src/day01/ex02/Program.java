package day01.ex02;

import java.math.BigDecimal;

public class Program {
    public static void main(String[] args) {
        User[] users = new User[10];

        for (int i = 0; i < users.length; i++) {
            users[i] = new User("User" + (i + 1), BigDecimal.valueOf(Math.random() * 100));
            System.out.println(users[i]);
        }

        UsersList usersList = new UsersArrayList();

        for (User user : users) {
            usersList.addUser(user);
        }

        User user5 = usersList.getUserById(5L);
        User user6 = usersList.getUserByIndex(6);

        System.out.println("User with id = 5: " + user5);
        System.out.println("User with index = 6: " + user6);


        try {
            User user11 = usersList.getUserById(11L);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        try {
            User user12 = usersList.getUserByIndex(12);
        }catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }
}
