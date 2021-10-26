package Lab02;

public class BinarySemaphore {
    int value;

    public BinarySemaphore(int initialValue) {
        value = initialValue;
    }

    public synchronized void P() throws InterruptedException {
        while(value <= 0) {
            wait();
        }
        value = 0;
    }

    public synchronized void V() {
        value = 1;
        notifyAll();
    }
}
