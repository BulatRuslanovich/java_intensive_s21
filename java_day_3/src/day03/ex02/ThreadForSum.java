package day03.ex02;

public class ThreadForSum implements Runnable {
    private final int from;
    private final int to;
    private final int index;
    private final Integer[] array;
    private int sum;

    public ThreadForSum(int from, int to, int index, Integer[] array) {
        this.from = from;
        this.to = to;
        this.index = index;
        this.array = array;
        sum = 0;
    }

    @Override
    public void run() {
        for (int i = from; i <= to; i++) {
            sum += array[i];
        }

        System.out.println("Thread " + index + ": from " + from + " to " + to + " sum is " + sum);

        synchronized(this) {
            Program.mainSum += sum;
        }
    }
}
