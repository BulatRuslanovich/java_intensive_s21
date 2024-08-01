package day01.ex01;
import java.math.BigDecimal;

public class Program {
    public static void main(String[] args) {
        User user1;
        User user2;

        for (int i = 0; i < 10; i++) {
            user1 = new User("Ryan Gosling " + i, BigDecimal.valueOf(100 * i));
            System.out.println(user1);
            user2 = new User("Antonio Margheriti " + i, BigDecimal.valueOf(200 * i));
            System.out.println(user2);
        }
    }
}
