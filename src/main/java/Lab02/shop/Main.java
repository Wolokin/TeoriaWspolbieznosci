package Lab02.shop;

import Lab02.buffer.Buffer;
import Lab02.buffer.Consumer;
import Lab02.buffer.Producer;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int clientCount = 6;
    public static final int cartCount = 2;

    public static void main(String[] args) throws InterruptedException {
        Shop shop = new Shop(cartCount);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < clientCount; ++i) {
            Thread t = new Thread(new Client(shop));
            threads.add(t);
            t.start();
        }

        for(Thread t : threads) {
            t.join();
        }
    }
}
