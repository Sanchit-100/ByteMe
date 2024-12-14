import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Order implements Comparable<Order> {
    private static int nextOrderNumber = 1;
    private int orderNumber;
    private List<MenuItem> items;
    private String specialRequests;
    private CustomerType orderType;
    private LocalDateTime confirmationTime;
    private OrderStatus status;

    public Order(String specialRequests, CustomerType orderType) {
        this.orderNumber = nextOrderNumber++;
        this.items = new ArrayList<>();
        this.specialRequests = specialRequests;
        this.orderType = orderType;
        this.confirmationTime = LocalDateTime.now();
        this.status = OrderStatus.PENDING;
    }

    // Getter and setter methods

    public int getOrderNumber() {
        return orderNumber;
    }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void removeItem(MenuItem item) {
        items.remove(item);
    }

    public double calculateTotal() {
        double total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public void setSpecialRequests(String specialRequests) {
        this.specialRequests = specialRequests;
    }

    public CustomerType getOrderType() {
        return orderType;
    }

    public void setOrderType(CustomerType orderType) {
        this.orderType = orderType;
    }

    public LocalDateTime getConfirmationTime() {
        return confirmationTime;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(Order other) {
        int typeComparison = this.orderType.compareTo(other.orderType);
        if (typeComparison != 0) {
            return typeComparison;
        }
        return Integer.compare(this.orderNumber, other.orderNumber);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String itemsString = items.stream()
                .map(MenuItem::toString)
                .collect(Collectors.joining(", "));
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", items=" + itemsString +
                ", specialRequests='" + specialRequests + '\'' +
                ", orderType=" + orderType +
                ", confirmationTime=" + confirmationTime.format(formatter) +
                ", status=" + status +
                '}';
    }

    public static Order fromString(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String regex = "orderNumber=(\\d+),.*?items=MenuItem\\{name=\"([^\"]+)\", price=([\\d.]+), category=\"([^\"]+)\", available=(true|false)\\}, specialRequests='([^']+)', orderType=([A-Za-z]+), confirmationTime=([\\d\\-:\\s]+), status=([A-Za-z]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);

        if (matcher.find()) {
            int orderNumber = Integer.parseInt(matcher.group(1));
            String itemName = matcher.group(2);
            double price = Double.parseDouble(matcher.group(3));
            String category = matcher.group(4);
            boolean available = Boolean.parseBoolean(matcher.group(5));
            String specialRequests = matcher.group(6);
            String orderType = matcher.group(7);
            String confirmationTimeStr = matcher.group(8);
            String statusStr = matcher.group(9);

            // Construct the items list from the parsed MenuItem attributes
            List<MenuItem> items = new ArrayList<>();
            items.add(new MenuItem(itemName, price, category, available));

            // Parse the LocalDateTime and enums
            LocalDateTime confirmationTime = LocalDateTime.parse(confirmationTimeStr, formatter);
            CustomerType orderTypeEnum = CustomerType.valueOf(orderType);
            OrderStatus statusEnum = OrderStatus.valueOf(statusStr);

            // Create the order object
            Order order = new Order(specialRequests, orderTypeEnum);
            order.orderNumber = orderNumber;
            order.items = items;
            order.confirmationTime = confirmationTime;
            order.status = statusEnum;

            return order;
        } else {
            throw new IllegalArgumentException("No match found!");
        }
    }
}
