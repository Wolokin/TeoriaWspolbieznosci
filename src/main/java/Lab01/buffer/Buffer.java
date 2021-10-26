package Lab01.buffer;

public class Buffer {

    private boolean empty = true;
    private String s;

    public synchronized void put(String s) throws InterruptedException {
        while (!empty) {
            this.wait();
        }
        empty = false;
        System.out.println("Full");
        this.s = s;
        this.notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while(empty) {
            this.wait();
        }
        empty = true;
        System.out.println("Empty");
        this.notifyAll();
        return s;
    }
}
