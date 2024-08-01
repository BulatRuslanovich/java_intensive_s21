package day03.ex02;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Program {
    public static int mainSum = 0;
    private static int MAX_NUM = 1000;

    public static void main(String[] args) {
        checkArgs(args);
        int arraySize = Integer.parseInt((args[0].split("=")[1]));
        int threadsCount = Integer.parseInt((args[1].split("=")[1]));
        if (arraySize > 2000000 || threadsCount > arraySize) {
            System.err.println("Error");
            System.exit(-3);
        }

        Integer[] arr = createArr(arraySize);
        displayArraySum(arr);

        int sumCount = arraySize / threadsCount;
        int start = 0;
        int end = sumCount;

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadsCount - 1; i++) {
            threads.add(new Thread(new ThreadForSum(start, end, i + 1, arr)));
            start += sumCount + 1;
            end += sumCount + 1;
        }

        threads.add(new Thread(new ThreadForSum(start, arraySize - 1, threadsCount, arr)));

        for (Thread th : threads) {
            th.start();
        }

        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Sum by threads: " + mainSum);
    }

    private static void displayArraySum(Integer[] arr) {
        int suma = 0;
        for (Integer integer : arr) {
            suma += integer;
        }

        System.out.println("Sum: " + suma);
    }

    private static Integer[] createArr(int size) {
        Random random = new Random(47);
        Integer[] arr = new Integer[size];
        int i = 0;
        while (i < size) {
            arr[i++] = random.nextInt(MAX_NUM - 1);
        }
        return arr;
    }

    private static void checkArgs(String[] args) {
        if (args.length != 2) {
            System.err.println("Error");
            System.exit(-1);
        }

        if (!args[0].matches("--arraySize=\\d++") || !args[1].matches("--threadsCount=\\d++")) {
            System.err.println("Error");
            System.exit(-2);
        }
    }
}
