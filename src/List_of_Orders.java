import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class List_of_Orders {
    private Queue<Order> orders;

    public List_of_Orders() {
        orders = new PriorityQueue<>();
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Order getNextOrder() {
        return orders.peek(); // View the next order without removing
    }

    public Order processNextOrder() {
        return orders.poll(); // Remove and return the next order
    }

    public void updateOrderStatus(Order order, OrderStatus newStatus) {
//        orders.remove(order);
        order.setStatus(newStatus);
//        orders.add(order);
    }

    public void listAllOrders() {
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public List<Order> getPendingOrders() {
        List<Order> allOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.PENDING) {
                allOrders.add(order);
            }
        }
        return allOrders;
    }

    public void listOrdersByStatus(OrderStatus status) {
        for (Order order : orders) {
            if (order.getStatus() == status) {
                System.out.println(order);
            }
        }
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename))) {
            for (Order order : orders) {
                writer.println(order.toString());
            }
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        orders.clear();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    orders.add(Order.fromString(line));
                }
            }
            System.out.println("Orders loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
