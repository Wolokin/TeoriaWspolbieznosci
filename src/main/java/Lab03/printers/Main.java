package Lab03.printers;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int COUNT = 10;
    private static final int printerCount = 2;
    private static final int clientCount = 7;

    public static void main(String[] args) throws InterruptedException {
        PrinterMonitor monitor = new PrinterMonitor(printerCount);
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < clientCount; ++i) {
            Thread t = new Thread(new Client(monitor));
            threads.add(t);
            t.start();
        }
        for(Thread t : threads) {
            t.join();
        }
    }
}
