package Lab04.stream_processing;

public class Producer extends Processor {
    public Producer(Buffer buffer, int buffer_size, int random) {
        super(buffer, buffer_size, -1, 0, random);
    }
}
