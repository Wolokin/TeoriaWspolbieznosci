package Lab02.buffer;

import static Lab02.buffer.Main.ILOSC;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            try {
                buffer.put("message "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Producer finished");

    }
}

