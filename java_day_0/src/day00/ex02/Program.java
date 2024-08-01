package day00.ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num;
        int counter = 0;
        while ((num = sc.nextInt()) != 42) {
            int sum = 0;

            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }

            boolean isSimple = true;
            for (int i = 2; i < Math.sqrt(sum) + 1; i++) {
                if (sum % i == 0) {
                    isSimple = false;
                    break;
                }
            }

            if (isSimple) counter++;
        }

        System.out.println("Count of coffee-request - " +  counter);
    }
}
