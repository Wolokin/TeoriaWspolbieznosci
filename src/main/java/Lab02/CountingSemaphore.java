package Lab02;

public class CountingSemaphore {
    int value;

    public CountingSemaphore(int initialValue) {
        value = initialValue;
    }

    public synchronized void P() throws InterruptedException {
        while(value <= 0) {
            wait();
        }
        value--;
    }

    public synchronized void V() {
        value++;
        notifyAll();
    }
}
