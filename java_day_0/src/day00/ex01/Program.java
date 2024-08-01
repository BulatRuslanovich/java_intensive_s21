package day00.ex01;

import java.util.Scanner;

public class Program {
    private static void valurCheck(int a) {
        if (a < 2) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        valurCheck(a);
        int counter = 0;
        boolean isSimple = true;

        for (int i = 2; i < Math.sqrt(a) + 1; i++) {
            counter++;

            if (a % i == 0) {
                isSimple = false;
                break;
            }
        }

        System.out.println(isSimple + " " + counter);
        System.exit(0);
    }

}