package day01.ex04;

import java.math.BigDecimal;

public class User {
    private final Long id;
    private final String name;
    private BigDecimal balance;
    private final TransactionsList transactionsList;

    public User(String name, BigDecimal balance) {
        this.transactionsList = new TransactionLinkedList();
        this.id = UserIdsGenerator.getInstance().generateUserId();
        this.name = name;
        this.balance = balance.max(BigDecimal.ZERO);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.max(BigDecimal.ZERO);
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
