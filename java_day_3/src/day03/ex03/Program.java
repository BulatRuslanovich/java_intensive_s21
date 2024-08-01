package day03.ex03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Program {
    private static final String FILE_URLS_NAME = "src/day03/ex03/urls.txt";
    private static final Map<String, Integer> urlMap = new LinkedHashMap<>();
    private static final ConcurrentLinkedQueue<String> uris = new ConcurrentLinkedQueue<>();

    private static void checkArgs(String[] args) {
        if (args.length != 1) {
            System.err.println("Error");
            System.exit(-1);
        }

        if (!args[0].matches("--threadsCount=\\d++")) {
            System.err.println("Error");
            System.exit(-2);
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        checkArgs(args);
        int threadCount = Integer.parseInt(args[0].split("=")[1]);
        File file = new File(FILE_URLS_NAME);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String stringBuff;
            int index = 0;

            while ((stringBuff = bufferedReader.readLine()) != null) {
                urlMap.put(stringBuff, ++index);
                uris.add(stringBuff);
            }

            for (int i = 0; i < threadCount; i++) {
                executorService.submit(new ThreadLoad(uris, urlMap, i + 1));
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

}
