package Lab02.buffer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int ILOSC = 5;

    private static final int producerCount = 5;
    private static final int consumerCount = 5;

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < producerCount; ++i) {
            Thread t = new Thread(new Producer(buffer));
            threads.add(t);
            t.start();
        }
        for(int i = 0; i < consumerCount; ++i) {
            Thread t = new Thread(new Consumer(buffer));
            threads.add(t);
            t.start();
        }

        for(Thread t : threads) {
            t.join();
        }
    }
}
