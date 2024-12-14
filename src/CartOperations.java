import java.util.Scanner;

public class CartOperations {
    private Menu menu;
    private CustomerManager customerManager;

    public CartOperations(Menu menu, CustomerManager customerManager) {
        this.menu = menu;
        this.customerManager = customerManager;
    }

    public void manageCart(Scanner scanner, String username) {
        Customer customer = customerManager.getCustomer(username);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        Order cart = new Order("No special requests", customer.getType()); // Temporary order for cart operations
        while (true) {
            System.out.println("\nCart Operations:");
            System.out.println("1. Add Item");
            System.out.println("2. Update Item");
            System.out.println("3. Remove Item");
            System.out.println("4. Checkout");
            System.out.println("0. Back to Customer Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                addItem(scanner, cart);
            } else if (choice == 2) {
                updateItem(scanner, cart);
            } else if (choice == 3) {
                removeItem(scanner, cart);
            } else if (choice == 4) {
                checkout(scanner, cart, customer);
                return;
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }

        }
    }

    private void addItem(Scanner scanner, Order cart) {
        System.out.println("Enter item name to add:");
        String name = scanner.nextLine();
        MenuItem item = menu.searchByName(name);
        if (item != null && item.isAvailable()) {
            cart.addItem(item);
            System.out.println("Item added to cart.");
        } else {
            System.out.println("Item not found or not available.");
        }


    }

    private void updateItem(Scanner scanner, Order cart) {
        System.out.println("Enter item name to update:");
        String name = scanner.nextLine();
        MenuItem item = menu.searchByName(name);
        if (item != null) {
            System.out.println("Enter new item name:");
            String newName = scanner.nextLine();
            System.out.println("Enter new item price:");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            System.out.println("Enter new item category:");
            String newCategory = scanner.nextLine();
            System.out.println("Is the item available (true/false):");
            boolean newAvailable = scanner.nextBoolean();
            scanner.nextLine(); // Consume newline

            MenuItem updatedItem = new MenuItem(newName, newPrice, newCategory, newAvailable);
            cart.removeItem(item);
            cart.addItem(updatedItem);
            System.out.println("Item updated in cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    private void removeItem(Scanner scanner, Order cart) {
        System.out.println("Enter item name to remove:");
        String name = scanner.nextLine();
        MenuItem item = menu.searchByName(name);
        if (item != null) {
            cart.removeItem(item);
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    private void checkout(Scanner scanner, Order cart, Customer customer) {
        System.out.println("Special requests:");
        String specialRequests = scanner.nextLine();
        cart.setSpecialRequests(specialRequests);
        customer.addOrder(cart);
        customerManager.getListOfOrders().addOrder(cart);
        System.out.println("Order checked out successfully.");
    }
}
