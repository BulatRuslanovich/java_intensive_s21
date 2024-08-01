package day01.ex00;

import java.math.BigDecimal;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Ryan Gosling");
        user1.setBalance(BigDecimal.valueOf(1000));
        User user2 = new User("Antonio Margheriti");
        user2.setBalance(BigDecimal.valueOf(2000));

        System.out.println(user1);
        System.out.println(user2);


        Transaction transaction = new Transaction(user1, user2, TransferCategory.CREDIT, BigDecimal.valueOf(100));
        System.out.println(transaction);
        Transaction transaction2 = new Transaction(user1, user2, TransferCategory.DEBIT, BigDecimal.valueOf(200));
        System.out.println(transaction2);
        Transaction transaction3 = new Transaction(user1, user2, TransferCategory.CREDIT, BigDecimal.valueOf(100000));
        System.out.println(transaction3);

        System.out.println(user1);
        System.out.println(user2);
    }
}
