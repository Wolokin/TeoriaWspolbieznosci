package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFairMy implements IBuffer {
    private int buffer = 0;
    private final int bufferSize;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition bufferHasSpace = lock.newCondition();
    private final Condition bufferHasObjects = lock.newCondition();

    public BufferFairMy(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void put(int val) throws InterruptedException {
        try {
            lock.lock();
            while (buffer + val > bufferSize) {
                bufferHasSpace.await();
            }
            buffer += val;
//            System.out.println("put "+ val);
            signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void get(int val) throws InterruptedException {
        try {
            lock.lock();
            while (buffer < val) {
                bufferHasObjects.await();
            }
            buffer -= val;
//            System.out.println("get "+ val);
            signal();
        }
        finally {
            lock.unlock();
        }
    }

    private void signal() {
        if(buffer < bufferSize/2) {
            bufferHasSpace.signal();
        }
        else if(buffer > bufferSize/2) {
            bufferHasObjects.signal();
        }
        else{
            bufferHasObjects.signal();
            bufferHasSpace.signal();
        }
    }
}
