package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BufferFairMy2 implements IBuffer {
    private int buffer = 0;
    private final int bufferSize;

    private final ReentrantLock lock = new ReentrantLock();
    private final DualGate putGate = new DualGate(lock.newCondition(), lock.newCondition());
    private final DualGate getGate = new DualGate(lock.newCondition(), lock.newCondition());
    private final Condition bufferHasSpace = lock.newCondition();
    private final Condition bufferHasObjects = lock.newCondition();

    public BufferFairMy2(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void put(int val) throws InterruptedException {
        try {
            lock.lock();
            putGate.go();
            while (buffer + val > bufferSize) {
                bufferHasSpace.await();
            }
            buffer += val;
            if(!lock.hasWaiters(bufferHasSpace)) {
                putGate.open();
            }
            signal();
        }
        finally {
            lock.unlock();
        }
    }

    public void get(int val) throws InterruptedException {
        try {
            lock.lock();
            getGate.go();
            while (buffer < val) {
                bufferHasObjects.await();
            }
            buffer -= val;
            if(!lock.hasWaiters(bufferHasObjects)) {
                getGate.open();
            }
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
