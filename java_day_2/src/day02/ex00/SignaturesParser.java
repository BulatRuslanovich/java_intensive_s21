package day02.ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignaturesParser {
    private static final Map<String, String> signatures = new HashMap<>();
    private static final String PATH = "src/day02/ex00/signatures.txt";

    public static Map<String, String> parseSignatures() {
        try {
          FileInputStream fileInputStream = new FileInputStream(PATH);
            Scanner scanner = new Scanner(fileInputStream);

            while (scanner.hasNext()) {
                signatures.put(scanner.next().replace(',', ' ').trim(), scanner.nextLine().trim());
            }

            scanner.close();
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return signatures;
    }

}
