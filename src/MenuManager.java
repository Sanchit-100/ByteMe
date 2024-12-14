import java.util.Scanner;

public class MenuManager {

    private Menu menu;

    public MenuManager(Menu menu) {
        this.menu = menu;
    }

    public void manageMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMenu Management:");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("0. Back to Admin Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addItem(scanner);
                    break;
                case 2:
                    removeItem(scanner);
                    break;
                case 3:
                    updateItem(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private void addItem(Scanner scanner) {
        System.out.println("Enter item name:");
        String name = scanner.nextLine();
        System.out.println("Enter item price:");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter item category:");
        String category = scanner.nextLine();
        System.out.println("Is the item available (true/false):");
        boolean available = scanner.nextBoolean();

        MenuItem item = new MenuItem(name, price, category, available);
        menu.addItem(item);
        System.out.println("Item added successfully.");
    }

    private void removeItem(Scanner scanner) {
        System.out.println("Enter the name of the item to remove:");
        String name = scanner.nextLine();
        menu.removeItem(name);
        System.out.println("Item removed successfully.");
    }

    private void updateItem(Scanner scanner) {
        System.out.println("Enter the name of the item to update:");
        String name = scanner.nextLine();

        System.out.println("Enter new item name:");
        String newName = scanner.nextLine();
        System.out.println("Enter new item price:");
        double newPrice = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.println("Enter new item category:");
        String newCategory = scanner.nextLine();
        System.out.println("Is the item available (true/false):");
        boolean newAvailable = scanner.nextBoolean();

        MenuItem updatedItem = new MenuItem(newName, newPrice, newCategory, newAvailable);
        menu.updateItem(name, updatedItem);
        System.out.println("Item updated successfully.");
    }
}
