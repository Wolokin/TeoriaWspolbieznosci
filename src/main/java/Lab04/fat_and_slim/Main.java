package Lab04.fat_and_slim;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static final long EXPERIMENT_TIME = 30000;
    public static final int[] MAX_OBJECT_SIZES = {1000, 10000, 100000};
    public static final int[] PRODUCER_COUNT = {10, 100, 1000};
    public static final int[] CONSUMER_COUNT = PRODUCER_COUNT;
    public static final IBuffer[] buffers = {
            new Buffer(MAX_OBJECT_SIZES[0] * 2),
            new Buffer(MAX_OBJECT_SIZES[1] * 2),
            new Buffer(MAX_OBJECT_SIZES[2] * 2),
            new BufferFairMy2(MAX_OBJECT_SIZES[0] * 2),
            new BufferFairMy2(MAX_OBJECT_SIZES[1] * 2),
            new BufferFairMy2(MAX_OBJECT_SIZES[2] * 2),
            new BufferFairBook(MAX_OBJECT_SIZES[0] * 2),
            new BufferFairBook(MAX_OBJECT_SIZES[1] * 2),
            new BufferFairBook(MAX_OBJECT_SIZES[2] * 2)

    };

    public static final Map<String, Integer> TESTS = Map.ofEntries(
            Map.entry("1_naive_t1.out", 0),
            Map.entry("1_naive_t2.out", 1),
            Map.entry("1_naive_t3.out", 2),
            Map.entry("2_my_t1.out", 3),
            Map.entry("2_my_t2.out", 4),
            Map.entry("2_my_t3.out", 5),
            Map.entry("3_book_t1.out", 6),
            Map.entry("3_book_t2.out", 7),
            Map.entry("3_book_t3.out", 8)
    );

    public static void main(String[] args) throws InterruptedException, IOException {
        for (Map.Entry<String, Integer> e : TESTS.entrySet()) {
            int k = e.getValue()%3;
            List<Thread> threads = new ArrayList<>();

            IBuffer buffer = buffers[e.getValue()];
            StatCollector producerStatCollector = new StatCollector();
            StatCollector consumerStatCollector = new StatCollector();

            for (int i = 0; i < PRODUCER_COUNT[k]; i++) {
                Producer producer = new Producer(buffer, producerStatCollector, MAX_OBJECT_SIZES[k]);
                threads.add(new Thread(producer));
            }
            for (int i = 0; i < CONSUMER_COUNT[k]; i++) {
                Consumer consumer = new Consumer(buffer, consumerStatCollector, MAX_OBJECT_SIZES[k]);
                threads.add(new Thread(consumer));
            }

            for (Thread thread : threads) {
                thread.start();
            }
            Thread.sleep(EXPERIMENT_TIME);
            for (Thread thread : threads) {
                thread.interrupt();
                thread.join();
            }
            FileWriter fileWriter = new FileWriter("./src/main/java/Lab04/fat_and_slim/out/" + e.getKey());
            fileWriter.write(producerStatCollector.toString());
            fileWriter.write(consumerStatCollector.toString());
            fileWriter.close();
            System.out.println("Wrote "+e.getKey());
        }
    }
}
