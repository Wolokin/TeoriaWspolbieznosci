package Lab03.printers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    private final boolean[] printerTaken;
    private int takenCount = 0;

    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();

    public PrinterMonitor(int printerCount) {
        printerTaken = new boolean[printerCount];
    }

    public int reserve() throws InterruptedException {
        lock.lock();
        while(takenCount == printerTaken.length) {
            notFull.await();
        }
        for(int i = 0; i < printerTaken.length; i++) {
            if(!printerTaken[i]) {
                printerTaken[i] = true;
                takenCount++;
                System.out.println("Printer " + i + " got reserved. Currently " + takenCount + " printers are reserved.");
                lock.unlock();
                return i;
            }
        }
        lock.unlock();
        return -1;
    }

    public void release(int id) {
        lock.lock();
        if(!printerTaken[id]) {
            lock.unlock();
            throw new IllegalArgumentException("Id " + id + " is not taken!");
        }
        printerTaken[id] = false;
        takenCount--;
        System.out.println("Printer " + id + " got released. Currently " + takenCount + " printers are reserved.");
        notFull.signal();
        lock.unlock();
    }
}
