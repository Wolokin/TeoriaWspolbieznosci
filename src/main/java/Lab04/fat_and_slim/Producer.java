package Lab04.fat_and_slim;

public class Producer extends Base{
    public Producer(IBuffer buffer, StatCollector statCollector, int maxObjSize) {
        super(buffer, statCollector, maxObjSize);
    }

    @Override
    void doStuff(int size) throws InterruptedException {
        buffer.put(size);
    }
}
