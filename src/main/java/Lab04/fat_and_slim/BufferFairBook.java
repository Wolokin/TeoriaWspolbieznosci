package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFairBook implements IBuffer{
    private int buffer = 0;
    private final int bufferSize;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition producerQueue = lock.newCondition();
    private final Condition consumerQueue = lock.newCondition();
    private final Condition bufferHasSpace = lock.newCondition();
    private final Condition bufferHasObjects = lock.newCondition();

    public BufferFairBook(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void put(int val) throws InterruptedException {
        try {
            lock.lock();
            while(lock.hasWaiters(bufferHasSpace)) {
                producerQueue.await();
            }
            while (buffer + val > bufferSize) {
                bufferHasSpace.await();
            }
            buffer += val;
//            System.out.println("put " + val + "\t/\t" + buffer);
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
            while(lock.hasWaiters(bufferHasObjects)) {
                consumerQueue.await();
            }
            while (buffer < val) {
                bufferHasObjects.await();
            }
            buffer -= val;
//            System.out.println("get " + val + "\t/\t" + buffer);
            consumerQueue.signal();
            bufferHasSpace.signal();
        }
        finally {
            lock.unlock();
        }
    }
}
