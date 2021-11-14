package Lab04.stream_processing;

public class Processor implements Runnable {
    private final Buffer buffer;
    private final int buffer_size;

    private final int consumes;
    private final int produces;

    private final int sleepingTime;

    public Processor(Buffer buffer, int buffer_size, int consumes, int produces, int sleepingTime) {
        this.buffer = buffer;
        this.buffer_size = buffer_size;
        this.consumes = consumes;
        this.produces = produces;
        this.sleepingTime = sleepingTime;
    }



    @Override
    public void run() {
        for(int i = 0; i < Main.LOOP; i++) {
            try {
                buffer.access(i % buffer_size).conditionalSet(consumes, produces);
                Thread.sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            buffer.print();
        }
    }
}
