package Lab04.stream_processing;

public class Consumer extends Processor {

    public Consumer(Buffer buffer, int buffer_size, int consumes, int random) {
        super(buffer, buffer_size, consumes, -1, random);
    }
}
