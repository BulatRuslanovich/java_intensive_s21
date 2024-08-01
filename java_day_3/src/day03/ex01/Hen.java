package day03.ex01;

public class Hen implements Runnable {
    private final int count;
    private final Freezer freezer;

    public Hen(int count, Freezer freezer) {
        this.count = count;
        this.freezer = freezer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println("Hen");
            freezer.freeze();
        }
    }
}
