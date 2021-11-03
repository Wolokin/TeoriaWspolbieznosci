package Lab03.table;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {
    private final int[] reservations;

    private final Lock lock = new ReentrantLock();
    private final Condition tableFree = lock.newCondition();
    private final Condition[] pairSignaling;

    private int tableTakenBy = -1;

    public Waiter(int pairCount) {
        reservations = new int[pairCount];
        pairSignaling = new Condition[pairCount];
        for (int i = 0; i < pairCount; i++) {
            pairSignaling[i] = lock.newCondition();
        }
    }

    public void reserve(int i) throws InterruptedException {
        lock.lock();
        System.out.println("Client from pair " + i + " has requested the table.");
        ++reservations[i];
        if (reservations[i] != 2) {
            while (reservations[i] != 2) {
                System.out.println(0 + " waiting for pair.");
                pairSignaling[i].await();
            }
            System.out.println("done waiting");
        } else if (reservations[i] == 2) {
            pairSignaling[i].signal();
            System.out.println("Clients from pair " + i + " can be assigned the table.");
        }
        while (tableTakenBy != -1 && tableTakenBy != i) {
            System.out.println("bad");
            tableFree.await();
        }
        System.out.println("done bad");
        System.out.println("The table is taken by pair " + i);
        tableTakenBy = i;
        lock.unlock();
    }

    public void release() {
        lock.lock();
        System.out.println("releasing");
        // This is to prevent the situation where one client goes away and then returns before the other one had the chance to leave the table
        if(reservations[tableTakenBy] == 2) {
            reservations[tableTakenBy] = -2;
        }
        System.out.println("Client from pair " + tableTakenBy + " left the table.");
        if (++reservations[tableTakenBy] >= 0) {
            tableTakenBy = -1;
            System.out.println("The table got freed.");
            tableFree.signalAll();
        }
        lock.unlock();
    }
}
