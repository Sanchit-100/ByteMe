import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class OrderManager {
    private static int nextOrderNumber = 1; // To assign order numbers

    public void manageOrders(Scanner scanner, List_of_Orders listOfOrders) {
        while (true) {
            System.out.println("\nOrder Management:");
            System.out.println("1. View Pending Orders");
            System.out.println("2. Change Order Status");
            System.out.println("0. Back to Admin Menu");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            if (choice == 1) {
                viewPendingOrders(listOfOrders);
            } else if (choice == 2) {
                changeOrderStatus(scanner, listOfOrders);
            } else if (choice == 0) {
                return;
            } else {
                System.out.println("Invalid selection. Please try again.");
            }

        }
    }

    public void viewPendingOrders(List_of_Orders listOfOrders) {
        listOfOrders.listOrdersByStatus(OrderStatus.PENDING);
    }




    private void changeOrderStatus(Scanner scanner, List_of_Orders listOfOrders) {
        System.out.println("Enter Order Number to update:");
        int orderNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Order orderToUpdate = findOrderByNumber(listOfOrders, orderNumber);
        if (orderToUpdate != null) {
            System.out.println("Select new status:");
            for (OrderStatus status : OrderStatus.values()) {
                System.out.println(status.ordinal() + 1 + ". " + status);
            }
            int statusChoice = scanner.nextInt();
            scanner.nextLine();

            if (statusChoice > 0 && statusChoice <= OrderStatus.values().length) {
                OrderStatus newStatus = OrderStatus.values()[statusChoice - 1];
                listOfOrders.updateOrderStatus(orderToUpdate, newStatus);
                System.out.println("Order status updated successfully.");
            } else {
                System.out.println("Invalid status selection.");
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private Order findOrderByNumber(List_of_Orders listOfOrders, int orderNumber) {
        for (Order order : listOfOrders.getOrders()) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        return null;
    }
}
