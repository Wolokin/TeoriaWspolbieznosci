package Lab04.stream_processing;

public class BufferField {
    private int val = -1;

    public synchronized void conditionalSet(int requiredVal, int resultingVal) throws InterruptedException {
        while(val != requiredVal) {
            this.wait();
        }
        val = resultingVal;
        this.notifyAll();
    }

    public int read() {
        return val;
    }
}
