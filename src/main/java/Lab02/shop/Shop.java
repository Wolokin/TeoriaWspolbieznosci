package Lab02.shop;

import Lab02.CountingSemaphore;

public class Shop {
    private final CountingSemaphore shoppingCarts;
    public Shop(int cartCount) {
        shoppingCarts = new CountingSemaphore(cartCount);
    }

    void cartTaken() throws InterruptedException {
        shoppingCarts.P();
    }

    void cartReturned() {
        shoppingCarts.V();
    }
}
