package Lab02.buffer;

import Lab02.BinarySemaphore;

public class Buffer {

    BinarySemaphore put_sem = new BinarySemaphore(1);
    BinarySemaphore take_sem = new BinarySemaphore(0);
    private String s;

    public void put(String s) throws InterruptedException {
        put_sem.P();
        System.out.println("Full");
        this.s = s;
        take_sem.V();
    }

    public String take() throws InterruptedException {
        take_sem.P();
        String res = s;
        System.out.println("Empty");
        put_sem.V();
        return res;
    }
}
