package day03.ex01;

public class Egg implements Runnable {
    private final int count;
    private final Freezer freezer;

    public Egg(int count, Freezer freezer) {
        this.count = count;
        this.freezer = freezer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Egg");
            freezer.freeze();
        }
    }
}
