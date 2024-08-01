package day00.ex04;

import java.util.Arrays;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        if (line.length() >= 999 || line.isEmpty()) {
            System.err.println("Illegal Argument!");
            System.exit(-1);
        }

        var charArray = line.toCharArray();
        var countArray = new int[0xffff];

        for (char c : charArray) {
            countArray[c]++;
        }

        var map = new int[charArray.length][2];
        int j = 0;
        for (var i = 0; i < countArray.length; i++) {
            if (countArray[i] != 0) {
                map[j][0] = i;
                map[j][1] = countArray[i];
                j++;
            }
        }

        Arrays.sort(map, (a, b) -> Integer.compare(b[1], a[1]));


        int maxFrequency = map[0][1];
        boolean isFirst = true;
        for (int i = 0; i < 10; i++) {
            if (isFirst) {
                System.out.print(maxFrequency + " ");
                isFirst = false;
            } else {
                System.out.print("   ");
            }
        }

        System.out.println();

        for (int i = 10; i > 0; i--) {
            for (int k = 0; k < 10; k++) {
                if (map[k][1] * 10 / maxFrequency >= i) {
                    System.out.print("#  ");
                } else if (map[k][1] * 10 / maxFrequency == i - 1) {
                    if (map[k][1] != 0) {
                        System.out.print(map[k][1] + "  ");
                    } else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }

        for (int i = 0; i < 10; i++) {
            if (map[i][0] != 0) {
                System.out.print((char) map[i][0] + "  ");
            }
        }


        System.exit(0);

    }
}
