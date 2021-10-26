package Lab02.shop;

import java.util.Random;

public class Client implements Runnable{
    Random rand = new Random();

    private final Shop shop;
    public Client(Shop shop) {
        this.shop = shop;
    }

    void goInside() throws InterruptedException {
        shop.cartTaken();
        System.out.println("I went inside");
    }

    void leave() {
        shop.cartReturned();
        System.out.println("I left");
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
            try {
                goInside();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000 + rand.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            leave();
        }
    }
}
