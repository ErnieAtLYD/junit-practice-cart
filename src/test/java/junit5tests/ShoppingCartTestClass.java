package junit5tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.strategio.Product;
import tech.strategio.ProductNotFoundException;
import tech.strategio.ShoppingCart;

public class ShoppingCartTestClass {
    @Test
    void testCartEmptyWhenCreated(){
        ShoppingCart cart = new ShoppingCart();
        Assertions.assertEquals(cart.getItemCount(), 0);
    }

    @Test
    void testCartEmptyWhenMethodempty(){
        ShoppingCart cart = new ShoppingCart();
        cart.addItem(new Product("bla", 1.0));
        cart.addItem(new Product("foo", 2.0));

        Assertions.assertEquals(cart.getItemCount(), 2);

        cart.empty();
        Assertions.assertEquals(cart.getItemCount(), 0);
    }

    @Test
    void testIncrementItemsWhenAdded(){
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(new Product("bla", 1.0));
        Assertions.assertEquals(cart.getItemCount(), 1);
    }

    @Test
    void testAddProductCostToCartBalance(){
        ShoppingCart cart = new ShoppingCart();
        var balance = cart.getBalance();
        cart.addItem(new Product("foo", 2.0));

        Assertions.assertEquals(cart.getBalance(), balance);
    }

    @Test
    void testRemoveProductCostfromCartBalance(){
        ShoppingCart cart = new ShoppingCart();

        cart.addItem(new Product("bla", 5.0));
        Product product = new Product("foo", 2.3);
        cart.addItem(product);

        var balance = cart.getBalance();
        try {
            cart.removeItem(product);
        } catch (ProductNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            Assertions.assertEquals(cart.getBalance(), balance-product.getPrice());
        }
    }


}
