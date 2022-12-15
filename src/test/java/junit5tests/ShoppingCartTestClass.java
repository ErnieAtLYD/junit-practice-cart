package junit5tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.strategio.ShoppingCart;

public class ShoppingCartTestClass {
    @Test
    void testCardEmptyWhenCreated(){
        ShoppingCart cart = new ShoppingCart();
        Assertions.assertEquals(cart.getItemCount(), 0);
    }
}
