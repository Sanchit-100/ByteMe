import java.util.Scanner;

public class BrowseMenu {

    private Menu menu;

    public BrowseMenu(Menu menu) {
        this.menu = menu;
    }

    public void displayMenuOptions(Scanner scanner) {
        while (true) {
            System.out.println("\nBrowse Menu:");
            System.out.println("1. View All Items");
            System.out.println("2. Sort by Price");
            System.out.println("3. Filter by Category");
            System.out.println("4. Search by Item Name");
            System.out.println("0. Back to Customer Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                viewAllItems();
            } else if (choice == 2) {
                sortByPrice();
            } else if (choice == 3) {
                filterByCategory(scanner);
            } else if (choice == 4) {
                searchByName(scanner);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }

        }
    }

    private void viewAllItems() {
        System.out.println("All menu items:");
        for (MenuItem item : menu.viewAllItems()) {
            System.out.println(item);
        }
    }

    private void sortByPrice() {
        System.out.println("Items sorted by price:");
        for (MenuItem item : menu.viewItemsSortedByPrice()) {
            System.out.println(item);
        }
    }

    private void filterByCategory(Scanner scanner) {
        System.out.println("Enter category to filter by:");
        String category = scanner.nextLine();
        System.out.println("Items in category " + category + ":");
        for (MenuItem item : menu.filterByCategory(category)) {
            System.out.println(item);
        }
    }

    private void searchByName(Scanner scanner) {
        System.out.println("Enter item name to search for:");
        String name = scanner.nextLine();
        MenuItem item = menu.searchByName(name);
        if (item != null) {
            System.out.println("Item found: " + item);
        } else {
            System.out.println("Item not found.");
        }
    }
}
