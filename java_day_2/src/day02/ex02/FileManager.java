package day02.ex02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;


public class FileManager {
    private Path path;

    public void run(String[] args) {
        checkArgs(args);
        path = Paths.get(args[0].substring("--current-folder=".length())).toAbsolutePath().normalize();
        checkPath(path);
        System.out.println(path);

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                scanInput(sc);
            }
        }
    }

    private void scanInput(Scanner sc) {
        String inputLine = sc.nextLine().trim();

        String[] inputs = inputLine.split("\\s+");

        if (inputs[0].equals("exit")) {
            System.exit(0);
        } else if (inputs[0].equals("ls")) {
            commandLS();
        } else if (inputs[0].equals("cd")) {
            if (inputs.length == 2) commandCD(inputs[1]);
            else System.err.println("cd: wrong number of args");
        } else if (inputs[0].equals("mv")) {
            if (inputs.length == 3) commandMV(inputs[1], inputs[2]);
            else System.err.println("mv: wrong number of args");
        } else {
            System.err.println("command not found: " + inputs[0]);
        }
    }

    private void commandMV(String what, String where) {
        Path source = null;

        try (Stream<Path> files = Files.list(path)) {
            source = files.filter(t -> t.getFileName().toString().equals(what))
                    .filter(Files::isRegularFile)
                    .findFirst()
                    .orElse(null);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (Objects.isNull(source)) {
            System.out.println("mv: cannot stat '" + what + "': No such file or directory");
            return;
        }

        Path target = isDir(where) ? path.resolve(where).resolve(source.getFileName()) : Paths.get(where);

        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean isDir(String where) {
        Path newPath = path.resolve(Paths.get(where)).normalize();
        return Files.isDirectory(newPath);
    }

    private void commandCD(String input) {
        Path newPath = path.resolve(Paths.get(input)).normalize();

        if (Files.isDirectory(newPath)) {
            path = newPath;
            System.out.println(path);
        } else {
            System.out.println("cd: not a dir: " + input);
        }
    }

    private void commandLS() {
        try (Stream<Path> files = Files.list(path)) {
            files.forEach(temp -> {
                        try {
                            long size = Files.isDirectory(path) ? dirSize(temp) : Files.size(temp);
                            System.out.println(temp.getFileName() + " " + (size / 1024) + " KB");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private long dirSize(Path path) throws IOException {
        return Files.walk(path)
                .filter(f -> f.toFile().isFile())
                .mapToLong(f -> f.toFile().length())
                .sum();
    }

    private void checkPath(Path path) {
        if (!Files.isDirectory(path)) {
            System.err.println("Error: Not a directory");
            System.exit(-1);
        }
    }

    private void checkArgs(String[] args) {
        if (args.length != 1 || !args[0].startsWith("--current-folder=")) {
            System.err.println("Error: Please provide exactly one argument in the format \"--current-folder=YOUR_FOLDER\".");
        }
    }
}
