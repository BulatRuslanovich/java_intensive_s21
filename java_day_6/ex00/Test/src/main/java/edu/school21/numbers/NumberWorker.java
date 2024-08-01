package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Num < 2");
        }

        double s = Math.sqrt(number);
        for (int i = 2; i < s + 1; i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    public int digitsSum(int number) {
        int sum = 0;

        number = Math.abs(number);

        for (; number > 0; number /= 10) {
            sum += number % 10;
        }

        return sum;
    }
}
