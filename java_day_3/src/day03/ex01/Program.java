package day03.ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error");
            System.exit(-1);
        }

        String[] input = args[0].split("=");

        if (!input[0].equals("--count")) {
            System.err.println("Error");
            System.exit(-1);
        }

        try {
            int count = Integer.parseInt(input[1]);
            Freezer freezer = new Freezer();
            Egg egg = new Egg(count, freezer);
            Hen hen = new Hen(count, freezer);

            new Thread(egg).start();
            new Thread(hen).start();
            new Thread(egg).join();
            new Thread(hen).join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
