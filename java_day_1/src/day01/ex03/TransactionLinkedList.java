package day01.ex03;

import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

public class TransactionLinkedList implements TransactionsList {
    private LinkedList<Transaction> transactionLinkedList;

    @Override
    public boolean add(Transaction transaction) {
        if (Objects.isNull(transactionLinkedList)) {
            this.transactionLinkedList = new LinkedList<>();
        }

        return this.transactionLinkedList.add(transaction);
    }

    @Override
    public boolean removeById(UUID id) {
        if (!this.transactionLinkedList.isEmpty()) {
            return this.transactionLinkedList.removeIf(transaction -> transaction.getTransactionId().equals(id));
        }

        return false;
    }

    @Override
    public Transaction[] toArray() {
        return transactionLinkedList.toArray(new Transaction[0]);
    }
}
