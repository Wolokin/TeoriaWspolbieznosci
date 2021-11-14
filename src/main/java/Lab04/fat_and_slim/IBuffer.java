package Lab04.fat_and_slim;

public interface IBuffer {
    void put(int val) throws InterruptedException;
    void get(int val) throws InterruptedException;
}
