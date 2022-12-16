package tech.strategio.tests;

import tech.strategio.Product;
import tech.strategio.ProductNotFoundException;
import tech.strategio.ShoppingCart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/*
When created, the cart has 0 items
When empty, the cart has 0 items
When a new product is added, the number of items must be incremented
When a new product is added, the new balance must be the sum of the previous balance plus the cost of the new product
When an item is removed, the number of items must be decreased
When a product not in the cart is removed, a ProductNotFoundException must be thrown
Hint: insert the call in a try block and put a fail() after the call to removeItem()
*/

class ShoppingCartTest {
    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
        // When created, the cart has 0 items
    void test_new_cart_is_empty() {
        assertEquals(0, cart.getItemCount());
    }

    @Test
        // When empty, the cart has 0 items
    void test_empty_cart_has_0_items() {
        cart.addItem(new Product("car", 42345));
        assertEquals(1, cart.getItemCount());
        cart.empty();
        assertEquals(0, cart.getItemCount());
    }

    @Test
        // When a new product is added, the number of items must be incremented
    void test_if_item_added_to_cart_increments() {
        cart.addItem(new Product("Celery", 4.99));
        assertEquals(1, cart.getItemCount());
        cart.addItem(new Product("Cheese", 2.49));
        assertEquals(2, cart.getItemCount());
        cart.addItem(new Product("Banana", 6.09));
        assertEquals(3, cart.getItemCount());
    }

    @Test
        // When a new product is added, the new balance must be the sum
        // of the previous balance plus the cost of the new product
    void test_new_product_added_balance() {
//        Alternate implementation
//        double sum = 0;
//        Product c = new Product("carrots", 10.20);
//        cart.addItem(c);
//        double prev_balance = cart.getBalance();
//        Product r = new Product("Radishes", 14.12);
//        cart.addItem(r);
//        double new_product = r.getPrice();
//        sum = prev_balance + new_product;
//        assertEquals(sum, cart.getBalance());

        assertEquals(0.0, cart.getBalance());
        cart.addItem(new Product("apple", 1.1));
        assertEquals(0.0 + 1.1, cart.getBalance());
        cart.addItem(new Product("banana", 2.2));
        assertEquals(0.0 + 1.1 + 2.2, cart.getBalance());
        cart.addItem(new Product("carrot", 3.3));
        assertEquals(0.0 + 1.1 + 2.2 + 3.3, cart.getBalance());
    }

    @Test
        // When an item is removed, the number of items must be decreased
    void test_if_item_deleted_from_cart_decrements() throws ProductNotFoundException {
        cart.addItem(new Product("water", 1.99));
        cart.addItem((new Product("pad thai", 21.50)));
        cart.addItem(new Product("snowmobile", 14320.99));
        assertEquals(3, cart.getItemCount());
        cart.removeItem(new Product("water", 999));
        assertEquals(2, cart.getItemCount());
        cart.removeItem(new Product("pad thai", 999));
        assertEquals(1, cart.getItemCount());
    }

    @Test
        // When a product not in the cart is removed, a ProductNotFoundException must be thrown
    void test_removing_from_empty_cart_throws() {
        boolean threw = false;
        try {
            cart.removeItem(new Product("organic radishes", 300.0));
            fail("removing item from empty cart should throw");
        } catch (ProductNotFoundException p) {
            threw = true;
        }
        assertTrue(threw);
    }

}