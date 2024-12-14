import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Report {
    private int numberOfOrders;
    private Map<String, Integer> itemsSold; // Map to store item names and their quantities sold
    private double totalSales;

    public Report() {
        this.numberOfOrders = 0;
        this.itemsSold = new HashMap<>();
        this.totalSales = 0.0;
    }

    public void generateReport(List_of_Orders listOfOrders) {
        Queue<Order> orders = listOfOrders.getOrders();
        for (Order order : orders) {
            numberOfOrders++;
            totalSales += order.calculateTotal();

            for (MenuItem item : order.getItems()) {
                itemsSold.put(item.getName(), itemsSold.getOrDefault(item.getName(), 0) + 1);
            }
        }
    }

    public void displayReport() {
        System.out.println("Daily Report:");
        System.out.println("Number of Orders: " + numberOfOrders);
        System.out.println("Items Sold:");
        for (Map.Entry<String, Integer> entry : itemsSold.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("Total Sales: $" + totalSales);
    }

    // Getters and Setters
    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public Map<String, Integer> getItemsSold() {
        return itemsSold;
    }

    public void setItemsSold(Map<String, Integer> itemsSold) {
        this.itemsSold = itemsSold;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}
