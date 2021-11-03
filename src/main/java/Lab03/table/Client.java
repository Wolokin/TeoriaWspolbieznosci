package Lab03.table;

public class Client implements Runnable {
    private final Waiter monitor;
    private final int id;

    public Client(Waiter monitor, int id) {
        this.monitor = monitor;
        this.id = id;
    }

    public void run() {
        for(int i = 0; i < Main.COUNT; i++) {
            try {
                monitor.reserve(id);
//                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monitor.release();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}

