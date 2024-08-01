package day01.ex04;


import java.math.BigDecimal;

public class Program {
    private static final String line = "===================================";

    public static void main(String[] args) {
        User user1 = new User("Ryan Gosling", BigDecimal.valueOf(80000));
        User user2 = new User("Antonio Margheriti", BigDecimal.valueOf(24000));

        TransactionsService transactionsService = new TransactionsService();
        transactionsService.addUser(user1);
        transactionsService.addUser(user2);

        transactionsService.executeTransaction(user1.getId(), user2.getId(), BigDecimal.valueOf(4532));
        transactionsService.executeTransaction(user2.getId(), user1.getId(), BigDecimal.valueOf(23456));
        transactionsService.executeTransaction(user1.getId(), user2.getId(), BigDecimal.valueOf(4566));

        System.out.println(transactionsService.getUserBalance(1L));
        System.out.println(transactionsService.getUserBalance(user2));

        System.out.println(line);

        for (Transaction transaction : transactionsService.getTransactionList(1L)) {
            System.out.println(transaction);
        }

        System.out.println(line);

        for (Transaction transaction : transactionsService.getTransactionList(2L)) {
            System.out.println(transaction);
        }

        System.out.println(line);

        Transaction[] checkTransactions = transactionsService.checkTransactions();

        for (Transaction transaction : checkTransactions) {
            System.out.println(transaction);
        }

        Transaction temp = transactionsService.getTransactionList(2L)[0];

        transactionsService.removeTransaction(temp.getTransactionId(), temp.getSender().getId());

        System.out.println(line);

        checkTransactions = transactionsService.checkTransactions();

        for (Transaction transaction : checkTransactions) {
            System.out.println(transaction);
        }
    }
}
