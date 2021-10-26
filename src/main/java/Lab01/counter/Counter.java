package Lab01.counter;

public class Counter {
    private int counter = 0;

    public synchronized void increment() {
        counter++;
    }

    public synchronized void decrement() {
        counter--;
    }

    public void print() {
        System.out.println(counter);
    }
}
