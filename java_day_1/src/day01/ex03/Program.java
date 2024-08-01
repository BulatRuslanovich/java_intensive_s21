package day01.ex03;

import java.math.BigDecimal;

public class Program {
    public static void main(String[] args) {
        User user1 = new User("Ryan Gosling", BigDecimal.valueOf(80000));
        User user2 = new User("Antonio Margheriti", BigDecimal.valueOf(24000));
        
        TransactionsList transactionsList = new TransactionLinkedList();
        Transaction transaction1 = new Transaction(user1, user2, TransferCategory.DEBIT, BigDecimal.valueOf(5000));
        transactionsList.add(transaction1);
        Transaction transaction2 = new Transaction(user1, user2, TransferCategory.CREDIT, BigDecimal.valueOf(4300));
        transactionsList.add(transaction2);
        Transaction transaction3 = new Transaction(user1, user2, TransferCategory.DEBIT, BigDecimal.valueOf(2300));
        transactionsList.add(transaction3);
        Transaction transaction4 = new Transaction(user1, user2, TransferCategory.CREDIT, BigDecimal.valueOf(5600));
        transactionsList.add(transaction4);
        Transaction transaction5 = new Transaction(user1, user2, TransferCategory.DEBIT, BigDecimal.valueOf(2300));
        transactionsList.add(transaction5);
        Transaction transaction6 = new Transaction(user1, user2, TransferCategory.CREDIT, BigDecimal.valueOf(6400));
        transactionsList.add(transaction6);

        transactionsList.removeById(transaction1.getTransactionId());
        transactionsList.removeById(transaction2.getTransactionId());
        transactionsList.removeById(transaction3.getTransactionId());
        transactionsList.removeById(transaction4.getTransactionId());

        Transaction[] transactionArray = transactionsList.toArray();
        for (Transaction entry: transactionArray) {
            System.out.println(entry);
        }
    }
}
