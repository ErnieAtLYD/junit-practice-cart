package test.strategio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.strategio.Product;
import tech.strategio.ProductNotFoundException;
import tech.strategio.ShoppingCart;

import java.util.List;

public class ShoppingCartTests {
    @Test
    public void testInitializeCart() {
        ShoppingCart cart = new ShoppingCart();

        double total = cart.getItemCount();

        Assertions.assertEquals(0, total);
    }

    @ParameterizedTest
    @MethodSource("getProductsArgument")
    public void testEmptyCart(List<Product> products) {
        ShoppingCart cart = new ShoppingCart();

        for (var product : products) {
            cart.addItem(product);
        }

        cart.empty();

        // Assert
        Assertions.assertEquals(0, cart.getItemCount());
    }

    @ParameterizedTest
    @MethodSource("getCountOfProductsArguments")
    public void testAddProduct(List<Product> products, IShoppingCartOperation operation) {
        ShoppingCart cart = new ShoppingCart();

        for (var product : products) {
            Assertions.assertTrue(operation.run(cart, product));
        }
    }

    @ParameterizedTest
    @MethodSource("getCheckCartBalanceArguments")
    public void testCheckCartBalance(List<Product> products, IShoppingCartOperation operation) {
        ShoppingCart cart = new ShoppingCart();

        for (var product : products) {
            Assertions.assertTrue(operation.run(cart, product));
        }
    }

    @ParameterizedTest
    @MethodSource("getRemoveProductArguments")
    public void testRemoveProduct(List<Product> products, IShoppingCartOperation operation) {
        ShoppingCart cart = new ShoppingCart();

        for (var product : products) {
            cart.addItem(product);
        }

        // test remove products that exist in cart
        for (var product : products) {
            Assertions.assertTrue(operation.run(cart, product));
        }
    }

    @ParameterizedTest
    @MethodSource("getProductsArgument")
    public void testRemoveProductException(List<Product> products) {
        ShoppingCart cart = new ShoppingCart();

        for (int i = 0; i < products.size() / 2; i++) {
            Product product = products.get(i);
            cart.addItem(product);
        }

        for (int i = products.size() / 2; i < products.size() ; i++) {
            Product product = products.get(i);
            Assertions.assertThrows(ProductNotFoundException.class, () -> cart.removeItem(product));
        }
    }

    // products data //
    private static List<Product> getProducts() {
        return List.of(
                new Product("Apple", 1.0),
                new Product("Orange", 2.0),
                new Product("Banana", 3.0),
                new Product("Pineapple", 4.0),
                new Product("Peach", 5.0),
                new Product("Pear", 6.0),
                new Product("Grape", 7.0),
                new Product("Strawberry", 8.0),
                new Product("Watermelon", 9.0),
                new Product("Mango", 10.0),
                new Product("Papaya", 11.0),
                new Product("Kiwi", 12.0),
                new Product("Lemon", 13.0),
                new Product("Lime", 14.0)
        );
    }

    // Arguments //
    private static List<List<Product>> getProductsArgument() {
        return List.of(getProducts());
    }

    private static List<Arguments> getCountOfProductsArguments() {
        return List.of(Arguments.of(getProducts(), getCountOfProductsProcedure()));
    }

    private static List<Arguments> getCheckCartBalanceArguments() {
        return List.of(Arguments.of(getProducts(), getCheckCartBalanceProcedure()));
    }

    private static List<Arguments> getRemoveProductArguments() {
        return List.of(Arguments.of(getProducts(), getRemoveProductProcedure()));
    }

    // procedures //
    private static IShoppingCartOperation getCountOfProductsProcedure() {
        return (cart, product) -> {
            var lastCount = cart.getItemCount();
            cart.addItem(product);
            return cart.getItemCount() == lastCount + 1;
        };
    }

    private static IShoppingCartOperation getCheckCartBalanceProcedure() {
        return (cart, product) -> {
            var lastBalance = cart.getBalance();
            cart.addItem(product);
            return Math.abs(cart.getBalance() - lastBalance - product.getPrice()) < 0.0001;
        };
    }

    private static IShoppingCartOperation getRemoveProductProcedure() {
        return (cart, product) -> {
            try {
                var lastCount = cart.getItemCount();
                cart.removeItem(product);
                return cart.getItemCount() == lastCount - 1;
            } catch (Exception e) {
                return e instanceof ProductNotFoundException;
            }
        };
    }
}

interface IShoppingCartOperation {
    boolean run(ShoppingCart cart, Product product);
}
