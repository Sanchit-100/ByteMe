import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerManager {
    private Map<String, Customer> customers;
    public List_of_Orders listOfOrders;
    private static final String FILE_NAME = "customers.txt";

    public CustomerManager() {
        customers = new HashMap<>();
        listOfOrders = new List_of_Orders();
        loadCustomersFromFile();
        customers.put("admin", new Customer("admin", "1234", CustomerType.REGULAR));
        // Register a shutdown hook to save customers on program exit
        Runtime.getRuntime().addShutdownHook(new Thread(this::saveCustomersToFile));
    }

    public void addCustomer(Customer customer) {
        customers.put(customer.getUsername(), customer);
        saveCustomersToFile();
    }

    public Customer getCustomer(String username) {
        return customers.get(username);
    }

    public void removeCustomer(String username) {
        customers.remove(username);
        saveCustomersToFile();
    }

    public void listCustomers() {
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }

    public boolean verifyAdmin(String username, String password) {
        Customer admin = customers.get("admin");
        return admin != null && admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }

    public boolean verifyCustomer(String username, String password) {
        Customer customer = customers.get(username);

        return customer != null && customer.getPassword().equals(password);
    }

    public boolean customerExists(String username) {
        return customers.containsKey(username);
    }

    public void addOrderToCustomer(String username, Order order) {
        Customer customer = customers.get(username);
        if (customer != null) {
            customer.addOrder(order);
            listOfOrders.addOrder(order); // Add to the global list of orders
        } else {
            System.out.println("Customer not found.");
        }
    }

    public List_of_Orders getListOfOrders() {
        return listOfOrders;
    }

    private void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer customer : customers.values()) {
                writer.write(customer.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Customer customer = Customer.fromString(line);
                customers.put(customer.getUsername(), customer);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Customers file not found. Starting with an empty list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
