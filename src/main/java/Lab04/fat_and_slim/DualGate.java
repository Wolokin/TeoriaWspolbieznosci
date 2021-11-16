package Lab04.fat_and_slim;

import java.util.concurrent.locks.Condition;

public class DualGate {
    private int waitingCount1 = 0;
    private int waitingCount2 = 0;
    private boolean isOpen1 = true;
    private boolean isOpen2 = true;

    private final Condition gate1;
    private final Condition gate2;

    DualGate(Condition gate1, Condition gate2) {
        this.gate1 = gate1;
        this.gate2 = gate2;
    }

    public void go() throws InterruptedException {
        if (!isOpen1) {
//            System.out.println(waitingCount1);
            waitingCount1++;
            while (!isOpen1) {
                gate1.await();
            }
            waitingCount1--;
        }
        if (waitingCount1 == 0) {
            isOpen1 = false;
            isOpen2 = true;
            gate2.signalAll();
        }
        if (!isOpen2) {
            waitingCount2++;
            while (!isOpen2) {
                gate2.await();
            }
            waitingCount2--;
        }
        if (waitingCount2 == 0) {
            isOpen2 = false;
        }
    }

    public void open() {
        isOpen1 = true;
        gate1.signalAll();
    }
}
