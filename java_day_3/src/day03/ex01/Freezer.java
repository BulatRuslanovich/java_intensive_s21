package day03.ex01;

public class Freezer {
    public synchronized void freeze() {
        try {
            notify();
            wait(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
