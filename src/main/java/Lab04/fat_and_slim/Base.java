package Lab04.fat_and_slim;

import java.util.Random;

public abstract class Base implements Runnable {
    private final Random random = new Random();
    protected final IBuffer buffer;
    private final StatCollector statCollector;
    private final int maxObjSize;

    public Base(IBuffer buffer, StatCollector statCollector, int maxObjSize) {
        this.buffer = buffer;
        this.statCollector = statCollector;
        this.maxObjSize = maxObjSize;
    }

    abstract void doStuff(int size) throws InterruptedException;

    @Override
    public void run() {
        while(true) {
            int size = random.nextInt(maxObjSize) + 1;
            long start = System.nanoTime();
            try {
                doStuff(size);
            } catch (InterruptedException e) {
//                e.printStackTrace();
                return;
            }
            long stop = System.nanoTime();
            statCollector.add(size, stop - start);
        }
    }
}
