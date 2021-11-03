package Lab03.table;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int COUNT = 100;
    private static final int pairCount = 5;

    public static void main(String[] args) throws InterruptedException {
        Waiter monitor = new Waiter(pairCount);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < pairCount; ++i) {
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
