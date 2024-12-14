import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CustomerManagerTest {
    private CustomerManager customerManager;

    @Before
    public void setUp() {
        customerManager = new CustomerManager();
        customerManager.addCustomer(new Customer("testUser", "correctPassword", CustomerType.REGULAR));
    }

    @Test
    public void testInvalidUsername() {
        // Attempt to log in with an incorrect username
        boolean result = customerManager.verifyCustomer("wrongUser", "correctPassword");
        assertFalse("Login should fail for invalid username", result);
    }

    @Test
    public void testInvalidPassword() {
        // Attempt to log in with an incorrect password
        boolean result = customerManager.verifyCustomer("testUser", "wrongPassword");
        assertFalse("Login should fail for invalid password", result);
    }

    @Test
    public void testNonExistingUser() {
        // Attempt to log in with a non-existing username
        boolean result = customerManager.verifyCustomer("nonExistingUser", "anyPassword");
        assertFalse("Login should fail for non-existing user", result);
    }
}
