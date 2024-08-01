package day00.ex03;

import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        int[] minArray = new int[18];

        for (int weekNum = 1; weekNum < 19 && !str.equals("42"); weekNum++) {
            if (!str.equals("Week " + weekNum)) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }

            str = sc.nextLine();
            minArray[weekNum - 1] = Arrays.stream(str.split(" "))
                    .mapToInt(Integer::parseInt)
                    .min()
                    .orElse(0);

            str = sc.nextLine();
        }

        int i = 0;
        while (minArray[i] != 0) {
            System.out.println("Week " + (i + 1) + " " + "=".repeat(minArray[i]) + ">");
            ++i;
        }

    }
}
