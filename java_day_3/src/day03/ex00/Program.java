package day03.ex00;

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

            Egg egg = new Egg(count);
            Hen hen = new Hen(count);

            egg.start();
            hen.start();
            egg.join();
            hen.join();

            for (int i = 0; i < count; i++) {
                System.out.println("Human");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
