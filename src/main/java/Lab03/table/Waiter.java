package Lab03.table;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final Lock lock = new ReentrantLock();
    private final Condition tableFree = lock.newCondition();
    private final Condition[] pairSignaling;

    private final boolean[] waitingForPair;
    private final boolean[] readyToEat;
    private final boolean[] partnerLeft;

    private int tableTakenBy = -1; // -1 -> the table is free

    public Waiter(int pairCount) {
        waitingForPair = new boolean[pairCount];
        readyToEat = new boolean[pairCount];
        partnerLeft = new boolean[pairCount];
        pairSignaling = new Condition[pairCount];
        for (int i = 0; i < pairCount; i++) {
            pairSignaling[i] = lock.newCondition();
        }
    }

    public void reserve(int i) throws InterruptedException {
        lock.lock();

        // Waiting for partner
        if (!waitingForPair[i]) {
            System.out.println("Client from pair " + i + " requested the table.");
            waitingForPair[i] = true;
            while (waitingForPair[i]) {
                pairSignaling[i].await();
            }
        } else {
            waitingForPair[i] = false;
            pairSignaling[i].signal();
            System.out.println("Client from pair " + i + " also requested the table.");
        }

        // Waiting for free table
        while (tableTakenBy != -1 && tableTakenBy != i) {
            tableFree.await();
        }
        tableTakenBy = i;

        // Eating together
        if (!readyToEat[i]) {
            System.out.println("The table is taken by pair " + i + ".");
            readyToEat[i] = true;
            while (readyToEat[i]) {
                pairSignaling[i].await();
            }
        } else {
            readyToEat[i] = false;
            pairSignaling[i].signal();
            System.out.println("Pair " + i + " is eating.");
        }
        lock.unlock();
    }

    public void release() {
        lock.lock();
        if (partnerLeft[tableTakenBy]) {
            System.out.println("Client from pair " + tableTakenBy + " left the table last.");
            partnerLeft[tableTakenBy] = false;
            tableTakenBy = -1;
            System.out.println("The table got freed.");
            tableFree.signalAll();
        } else {
            System.out.println("Client from pair " + tableTakenBy + " left the table first.");
            partnerLeft[tableTakenBy] = true;
        }
        lock.unlock();
    }
}
