import org.junit.jupiter.api.*;
import tech.strategio.Product;
import tech.strategio.ProductNotFoundException;
import tech.strategio.ShoppingCart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartTest {
    ShoppingCart cart;

    @BeforeEach
    void beforeEach() {
        cart = new ShoppingCart();
        cart.addItem(new Product("lettuce", 2.5));
        cart.addItem(new Product("spinach", 3.0));
        cart.addItem(new Product("tomatoes", 4.0));
        cart.addItem(new Product("cucumber", 5.0));
    }

    @Test
    @DisplayName("Is empty when created")
    void isEmptyWhenCreated() {
        ShoppingCart newCart = new ShoppingCart();
        assertEquals(0, newCart.getItemCount());
    }

    @Test
    @DisplayName("Is empty when emptied")
    void isEmptyWhenEmptied() {
        cart.empty();
        assertEquals(0, cart.getItemCount());
    }

    @Test
    @Order(1)
    @DisplayName("Increases in size when item is added")
    void increasesSizeWhenItemIsAdded() {
        int initialCount = cart.getItemCount();
        cart.addItem(new Product("eggplant", 4.0));
        assertEquals(initialCount + 1, cart.getItemCount());
    }

    @Test
    @DisplayName("Decreases in size when item is removed")
    void decreasesSizeWhenItemIsRemoved() throws ProductNotFoundException {
        int initialCount = cart.getItemCount();

        Product eggplant = new Product("eggplant", 4.0);
        cart.addItem(eggplant);

        cart.removeItem(eggplant);
        assertEquals(initialCount, cart.getItemCount());
    }

    @Test
    @DisplayName("Increases in balance when item is added")
    void increasesInBalanceWhenItemIsAdded() {
        double initialBalance = cart.getBalance();

        double eggplantPrice = 4.0;
        Product eggplant = new Product("eggplant", eggplantPrice);
        cart.addItem(eggplant);

        assertEquals(initialBalance + eggplantPrice, cart.getBalance());
    }

    @Test
    @DisplayName("Throws ProductNotFoundException when item to remove is not found")
    void throwsExceptionWhenItemToRemoveIsNotFound() {
        Product eggplant = new Product("eggplant", 4.0);

        assertThrows(ProductNotFoundException.class,
                () -> cart.removeItem(eggplant));
    }

}
