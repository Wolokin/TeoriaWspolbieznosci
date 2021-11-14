package Lab04.stream_processing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int LOOP = 10000;
    public static final int BUFFER_SIZE = 20;
    public static final int PROCESSOR_COUNT = 10;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Random random = new Random(2);

        Buffer buffer = new Buffer(BUFFER_SIZE);
        new Thread(buffer).start();

        Producer producer = new Producer(buffer, BUFFER_SIZE, 10);
        threads.add(new Thread(producer));
        for(int i = 0; i < PROCESSOR_COUNT; i++) {
            Processor processor = new Processor(buffer, BUFFER_SIZE, i,i+1, random.nextInt(1000));
            threads.add(new Thread(processor));
        }
        Consumer consumer = new Consumer(buffer, BUFFER_SIZE, PROCESSOR_COUNT, random.nextInt(1000));
        threads.add(new Thread(consumer));

        for(Thread thread : threads) {
            thread.start();
        }
        for(Thread thread : threads) {
            thread.join();
        }
    }
}
