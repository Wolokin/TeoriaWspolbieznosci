package Lab04.stream_processing;

public class Buffer  implements Runnable {
    private final BufferField[] buffer;

    Buffer(int size) {
        buffer = new BufferField[size];
        for(int i = 0; i < size; i++) {
            buffer[i] = new BufferField();
        }
    }

    public BufferField access(int ind) {
        return buffer[ind];
    }

    public synchronized void print() {
        System.out.print("[");
        for(BufferField bufferField : buffer) {
            System.out.print(bufferField.read() + ", ");
        }
        System.out.println("]");
    }

    @Override
    public void run() {
        while(true) {
            print();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
