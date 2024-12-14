import java.util.Scanner;

public class OrderTrack {

    private CustomerManager customerManager;

    public OrderTrack(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    public void manageOrders(Scanner scanner, String username) {
        Customer customer = customerManager.getCustomer(username);
        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        while (true) {
            System.out.println("\nOrder Tracking:");
            System.out.println("1. View Order Status");
            System.out.println("2. Cancel Order");
            System.out.println("3. View Order History");
            System.out.println("0. Back to Customer Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                viewOrderStatus(scanner, customer);
            } else if (choice == 2) {
                cancelOrder(scanner, customer);
            } else if (choice == 3) {
                viewOrderHistory(customer);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }

        }
    }

    private void viewOrderStatus(Scanner scanner, Customer customer) {
        System.out.println("Enter Order Number to view status:");
        int orderNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Order order = findOrderByNumber(customer, orderNumber);
        if (order != null) {
            System.out.println("Order Status: " + order.getStatus());
            System.out.println("Order Details:\n" + order);
        } else {
            System.out.println("Order not found.");
        }
    }

    private void cancelOrder(Scanner scanner, Customer customer) {
        System.out.println("Enter Order Number to cancel:");
        int orderNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Order order = findOrderByNumber(customer, orderNumber);
        if (order != null) {
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.CANCELLED);
                System.out.println("Order cancelled successfully.");
            } else {
                System.out.println("Order cannot be cancelled. Current status: " + order.getStatus());
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private void viewOrderHistory(Customer customer) {
        System.out.println("Order History:");
        for (Order order : customer.getOrderHistory()) {
            System.out.println(order);
        }
    }

    private Order findOrderByNumber(Customer customer, int orderNumber) {
        for (Order order : customer.getOrderHistory()) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
}
