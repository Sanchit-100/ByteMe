import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

public class CartOperationsTest {
    private Menu menu;
    private CustomerManager customerManager;
    private CartOperations cartOperations;
    private Order cart;

    @Before
    public void setUp() {
        menu = new Menu(); // Initialize Menu
        customerManager = new CustomerManager();
        cartOperations = new CartOperations(menu, customerManager);

        // Add an unavailable item to the menu
        MenuItem unavailableItem = new MenuItem("Unavailable Item", 10.0, "Category", false);
        menu.addItem(unavailableItem);

        Customer testCustomer = new Customer("testUser", "testPassword", CustomerType.REGULAR);
        customerManager.addCustomer(testCustomer);
        cart = new Order("No special requests", CustomerType.REGULAR); // Initialize cart
    }

    @Test
    public void testAddUnavailableItem() {
        // Simulate user input for adding an unavailable item
        String input = "1\nUnavailable Item\n0\n"; // Choice 1 (Add Item), Item Name, then Back to Customer Menu
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        // Redirect System.out to capture the output
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        cartOperations.manageCart(scanner, "testUser");

        // Verify that the item was not added to the cart
        assertTrue("Item should not have been added to the cart",cart.getItems().isEmpty());

        // Verify the output contains the expected error message
        assertTrue("Wrong output message", outContent.toString().contains("Item not found or not available."));
    }
}
