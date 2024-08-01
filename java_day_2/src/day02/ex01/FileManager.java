package day02.ex01;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

public class FileManager {
    private final String firstFilePath;
    private final String secondFilePath;


    public FileManager(String firstFilePath, String secondFilePath) {
        this.firstFilePath = firstFilePath;
        this.secondFilePath = secondFilePath;
    }

    public double countOfIncludes() {
        try {
            String commonPath = "src/day02/ex01/";
            Set<String> set = new HashSet<>();
            set.addAll(readWords(commonPath + firstFilePath));
            set.addAll(readWords(commonPath + secondFilePath));

            int[] entriesA = getArrayOfFrequency(set, commonPath + firstFilePath);
            int[] entriesB = getArrayOfFrequency(set, commonPath + secondFilePath);

            return countSimilarity(entriesA, entriesB);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] getArrayOfFrequency(Set<String> set, String filePath) throws IOException {
        int[] frequencies = new int[set.size()];
        Map<String, Integer> indexMap = new HashMap<>();

        int i = 0;

        for (String word : set) {
            indexMap.put(word, i++);
        }

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNext()) {
                String word = scanner.next();
                Integer index = indexMap.get(word);

                if (!Objects.isNull(index)) {
                    frequencies[index]++;
                }
            }
        }

        return frequencies;
    }

    private Set<String> readWords(String filePath) throws IOException {
        Set<String> words = new HashSet<>();
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
        Scanner scanner = new Scanner(fileInputStream)) {
            while (scanner.hasNext()) {
                words.add(scanner.next());
            }
        }

        return words;
    }

    private double countSimilarity(int[] entriesA, int[] entriesB) {
        int numerator = IntStream.range(0, entriesA.length)
                .map(i -> entriesA[i] * entriesB[i])
                .sum();

        int denominatorA = Arrays.stream(entriesA)
                .map(i -> i * i)
                .sum();

        int denominatorB = Arrays.stream(entriesB)
                .map(i -> i * i)
                .sum();

        return numerator / (Math.sqrt(denominatorA) * Math.sqrt(denominatorB));
    }
}
