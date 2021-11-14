package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer implements IBuffer{
    private int buffer = 0;
    private final int bufferSize;

    private final Lock lock = new ReentrantLock();
    private final Condition bufferHasSpace = lock.newCondition();
    private final Condition bufferHasObjects = lock.newCondition();

    public Buffer(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void put(int val) throws InterruptedException {
        try {
            lock.lock();
            while (buffer + val > bufferSize) {
                bufferHasSpace.await();
            }
            buffer += val;
//            System.out.println("put " + val + "\t/\t" + buffer);
            bufferHasObjects.signalAll();
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
//            System.out.println("get " + val + "\t/\t" + buffer);
            bufferHasSpace.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
}
