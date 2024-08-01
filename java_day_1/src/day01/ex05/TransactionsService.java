package day01.ex05;


import day01.ex05.enums.TransferCategory;

import java.math.BigDecimal;
import java.util.UUID;

public class TransactionsService {
    private UsersList usersList = new UsersArrayList();

    public void addUser(User user) {
        usersList.add(user);
    }

    public BigDecimal getUserBalance(Long userId) {
        return usersList.getUserById(userId).getBalance();
    }

    public BigDecimal getUserBalance(User user) {
        return getUserBalance(user.getId());
    }

    public Transaction executeTransaction(Long senderId, Long receiverId, BigDecimal amount) {
        return new Transaction(usersList.getUserById(senderId), usersList.getUserById(receiverId), TransferCategory.CREDIT, amount);
    }

    public Transaction[] checkTransactions() {
        TransactionsList transactionsList = getAllTransactions();
        TransactionsList res = new TransactionLinkedList();

        for (Transaction transaction : transactionsList.toArray()) {
            if (countEquals(transaction.getTransactionId(), transactionsList.toArray()) != 2) {
                res.add(transaction);
            }
        }

        return res.toArray();
    }

    private int countEquals(UUID transactionId, Transaction[] array) {
        int count = 0;

        for (Transaction transaction : array) {
            if (transaction.getTransactionId().equals(transactionId)) {
                count++;
            }
        }

        return count;
    }


    public Transaction[] getTransactionList(Long userId) {
        return usersList.getUserById(userId).getTransactionsList().toArray();
    }

    public void removeTransaction(UUID transactionId, Long userId) {
        usersList.getUserById(userId).getTransactionsList().removeById(transactionId);
    }

    public TransactionsList getAllTransactions() {
        TransactionsList transactionsList = new TransactionLinkedList();

        for (int i = 0; i < usersList.getUserCount(); ++i) {
            for (Transaction transaction : usersList.getUserByIndex(i).getTransactionsList().toArray()) {
                transactionsList.add(transaction);
            }
        }

        return transactionsList;
    }

    public UsersList getUsersList() {
        return usersList;
    }
}
