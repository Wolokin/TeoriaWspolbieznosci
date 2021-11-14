package Lab04.fat_and_slim;

public class Consumer extends Base{
    public Consumer(IBuffer buffer, StatCollector statCollector, int maxObjSize) {
        super(buffer, statCollector, maxObjSize);
    }

    @Override
    void doStuff(int size) throws InterruptedException {
        buffer.get(size);
    }
}
