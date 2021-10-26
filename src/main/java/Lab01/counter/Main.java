package Lab01.counter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter c = new Counter();
        int n = 10000000;
        Thread inc = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < n; ++i) {
                    c.increment();
                }
            }
        });
        Thread dec = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < n; ++i) {
                    c.decrement();
                }
            }
        });
        inc.start();
        dec.start();
        inc.join();
        dec.join();

        c.print();
    }
}
