import java.util.PriorityQueue;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String username;
    private String password;
    private CustomerType type;
    private Queue<Order> orderHistory;

    public Customer(String username, String password, CustomerType type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.orderHistory = new PriorityQueue<>();
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for type
    public CustomerType getType() {
        return type;
    }

    public void setType(CustomerType type) {
        this.type = type;
    }

    // Getter for order history
    public Queue<Order> getOrderHistory() {
        return orderHistory;
    }

    // Method to add an order to the order history
    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    @Override
    public String toString() {
        // Serialize order history as a string
        StringBuilder orderHistoryString = new StringBuilder();
        for (Order order : orderHistory) {
            if (orderHistoryString.length() > 0) {
                orderHistoryString.append("|"); // Separate orders with '|'
            }
            orderHistoryString.append(order.toString());
        }

        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", type=" + type +
                ", orderHistory=[" + orderHistoryString.toString() + "]" +
                '}';
    }

    public static Customer fromString(String data) {
        Pattern pattern = Pattern.compile("Customer\\{username='([^']+)', password='([^']+)', type=([^,]+), orderHistory=\\[(.*)\\]\\}");
        Matcher matcher = pattern.matcher(data);
        if (matcher.find()) {
        String username = matcher.group(1);
        String password = matcher.group(2);
        CustomerType type = CustomerType.valueOf(matcher.group(3));
        String orderHistoryData = matcher.group(4);

        Customer customer = new Customer(username, password, type);

        // Deserialize order history
        if (!orderHistoryData.isEmpty()) {
            String[] orders = orderHistoryData.split("\\|");
            for (String orderData : orders) {
                customer.addOrder(Order.fromString(orderData));
            }
        }

        return customer;
    } else {
        throw new IllegalArgumentException("Invalid input data: " + data);
    }
}
}
