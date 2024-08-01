package day03.ex03;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadLoad implements Runnable {
    private final ConcurrentLinkedQueue<String> uris;
    private final Map<String, Integer> map;
    private final Integer index;

    public ThreadLoad(ConcurrentLinkedQueue<String> uris, Map<String, Integer> map, Integer index) {
        this.uris = uris;
        this.map = map;
        this.index = index;
    }

    @Override
    public void run() {
        while (!uris.isEmpty()) {
            String uri = uris.poll();
            Integer fileNum = map.get(uri);
            String filename = uri.substring(uri.lastIndexOf('/') + 1);

            System.out.printf("Thread-%d start download file number %d\n", index, fileNum);
            try (InputStream is = new URL(uri).openStream()) {
                Files.copy(is, Paths.get(filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.printf("Thread-%d finish download file number %d\n", index, fileNum);
        }
    }
}
