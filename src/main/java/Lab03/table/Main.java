package Lab03.table;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int COUNT = 1000;
    private static final int PAIR_COUNT = 5;

    public static void main(String[] args) throws InterruptedException {
        Waiter monitor = new Waiter(PAIR_COUNT);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < PAIR_COUNT; ++i) {
            Thread t = new Thread(new Client(monitor, i));
            threads.add(t);
            t.start();
            t = new Thread(new Client(monitor, i));
            threads.add(t);
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }
    }
}
