package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFairMy3 implements IBuffer{
    private int buffer = 0;
    private final int bufferSize;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerQueue = lock.newCondition();
    private final Condition consumerQueue = lock.newCondition();
    private final Condition bufferHasSpace = lock.newCondition();
    private final Condition bufferHasObjects = lock.newCondition();

    public BufferFairMy3(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void put(int val) throws InterruptedException {
        try {
            lock.lock();
            if(buffer > 0) {
                producerQueue.await();
            }
            while (buffer + val > bufferSize) {
                bufferHasSpace.await();
            }
            buffer += val;
            producerQueue.signal();
            bufferHasObjects.signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void get(int val) throws InterruptedException {
        try {
            lock.lock();
            if(buffer < bufferSize) {
                consumerQueue.await();
            }
            while (buffer < val) {
                bufferHasObjects.await();
            }
            buffer -= val;
            consumerQueue.signal();
            bufferHasSpace.signal();
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
