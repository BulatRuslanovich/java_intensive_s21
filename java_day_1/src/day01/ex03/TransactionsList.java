package day01.ex03;

import java.util.UUID;

public interface TransactionsList {
    boolean add(Transaction transaction);
    boolean removeById(UUID id);
    Transaction[] toArray();
}
