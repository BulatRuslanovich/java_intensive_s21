package day01.ex05;

import java.util.UUID;

public interface TransactionsList {
    boolean add(Transaction transaction);
    boolean removeById(UUID id);
    Transaction[] toArray();
}
