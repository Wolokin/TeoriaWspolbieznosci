package Lab01.buffer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int ILOSC = 100;

    public static final int producerCount = 5;
    public static final int consumerCount = 5;

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
