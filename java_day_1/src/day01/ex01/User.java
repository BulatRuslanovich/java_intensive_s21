package day01.ex01;

import java.math.BigDecimal;

public class User {
    private final String name;
    private final Long userId;
    private BigDecimal balance;

    public User(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance.max(BigDecimal.ZERO);
        this.userId = UserIdsGenerator.getInstance().generateUserId();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}
