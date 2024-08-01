package day01.ex00;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final String name;
    private BigDecimal balance;

    public User(String name) {
        this.userId = UUID.randomUUID();
        this.name = name;
        this.balance = BigDecimal.valueOf(0);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(name, user.name) && Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, balance);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
