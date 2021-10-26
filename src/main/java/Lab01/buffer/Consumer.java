package Lab01.buffer;

import static Lab01.buffer.Main.ILOSC;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private int consumed = 0;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            try {
                String message = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            consumed++;
        }
        System.out.println("Consumer finished");
    }
}
