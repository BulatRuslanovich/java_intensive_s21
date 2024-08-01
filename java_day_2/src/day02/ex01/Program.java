package day02.ex01;

public class Program {
    public static void main(String[] args) {
        FileManager fileManager = new FileManager(args[0], args[1]);
        System.out.println("Similarity = " + Math.floor(fileManager.countOfIncludes() * 100) / 100.0);
    }
}
