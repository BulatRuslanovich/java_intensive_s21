package day02.ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Analyzer {
    public static void read(String path) {
        byte[] bytes = new byte[8];

        try (FileInputStream inputStream = new FileInputStream(path)) {
            if (inputStream.read(bytes) != -1) {
                StringBuilder builder = printBytesToHex(bytes);
                compareBytes(builder);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compareBytes(StringBuilder builder) {
        Map<String, String> map = SignaturesParser.parseSignatures();


        try (FileOutputStream outputStream = new FileOutputStream("src/day02/ex00/result.txt", true)) {

            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (builder.toString().contains(entry.getValue())) {
                    outputStream.write(entry.getKey().getBytes());
                    outputStream.write('\n');
                    System.out.println("PROCESSED");
                    outputStream.close();
                    return;
                }
            }

            System.out.println("UNDEFINED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static StringBuilder printBytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (byte b : bytes) {
            builder.append(String.format("%02X", b)).append(" ");
        }

        builder.deleteCharAt(builder.length() - 1);
        return builder;
    }
}
