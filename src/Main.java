import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static Menu menu = new Menu();
    private static MenuManager menuManager = new MenuManager(menu);
//    private static List_of_Orders listOfOrders = new List_of_Orders();
    private static OrderManager orderManager = new OrderManager();
    private static BrowseMenu browseMenu = new BrowseMenu(menu);
    private static CustomerManager customerManager = new CustomerManager();
    private static CartOperations cartOperations = new CartOperations(menu, customerManager);
    private static OrderTrack orderTrack = new OrderTrack(customerManager);
    private static Report report = new Report();
    private static final String MENU_FILE = "menu.txt";
    private static final String ORDERS_FILE = "orders.txt";

    public static void main(String[] args) {
        // Load menu from file when program starts
//        System.out.println("Hello");
        loadMenu();
        loadOrders();

        // Register a shutdown hook to save menu to file when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            saveMenu();
            saveOrders();
        }));

        // Run the CLI as usual
        runCLI();
    }

    private static void runCLI() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome! Please select your role:");
            System.out.println("1. Customer Sign Up");
            System.out.println("2. Customer Log In");
            System.out.println("3. Admin");
            System.out.println("0. Exit");

            int role = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (role) {
                case 1:
                    customerSignUp(scanner);
                    break;
                case 2:
                    customerLogIn(scanner);
                    break;
                case 3:
                    System.out.println("Enter admin username:");
                    String adminUsername = scanner.nextLine();
                    System.out.println("Enter admin password:");
                    String adminPassword = scanner.nextLine();
                    if (customerManager.verifyAdmin(adminUsername, adminPassword)) {
                        adminMenu(scanner);
                    } else {
                        System.out.println("Invalid admin credentials.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void customerSignUp(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        Customer customer = new Customer(username, password, CustomerType.REGULAR);
        customerManager.addCustomer(customer);
        System.out.println("Sign-up successful! Please log in.");
    }

    private static void customerLogIn(Scanner scanner) {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        Customer customer = customerManager.getCustomer(username);
        if (customer != null && customer.getPassword().equals(password)) {
            customerMenu(scanner, username);
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void customerMenu(Scanner scanner, String username) {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("1. Browse Menu");
            System.out.println("2. Cart Operations");
            System.out.println("3. Order Track");
            System.out.println("4. Review Item");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                browseMenu.displayMenuOptions(scanner);
            } else if (choice == 2) {
                cartOperations.manageCart(scanner, username);
            } else if (choice == 3) {
                orderTrack.manageOrders(scanner, username);
            } else if (choice == 4) {
                System.out.println("Reviewing item...");
                // Add review item logic here
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Manage Menu");
            System.out.println("2. Manage Orders");
            System.out.println("3. Generate Report");
            System.out.println("0. Back to Main Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                menuManager.manageMenu(scanner);
            } else if (choice == 2) {
                orderManager.manageOrders(scanner, customerManager.getListOfOrders());
            } else if (choice == 3) {
                System.out.println("Generating report...");
                report.generateReport(customerManager.getListOfOrders());
                report.displayReport();
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // Save menu to file
    private static void saveMenu() {
        menu.saveToFile(MENU_FILE);
    }

    // Load menu from file
    private static void loadMenu() {
        menu.loadFromFile(MENU_FILE);
    }

    private static void saveOrders() {
        customerManager.listOfOrders.saveToFile(ORDERS_FILE);
    }

    private static void loadOrders() {
        customerManager.listOfOrders.loadFromFile(ORDERS_FILE);
    }
}
