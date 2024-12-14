import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private List<MenuItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    // Admin-only functionalities

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(String itemName) {
        items.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public void updateItem(String itemName, MenuItem updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(itemName)) {
                items.set(i, updatedItem);
                return;
            }
        }
    }

    // Customer functionalities

    public List<MenuItem> viewAllItems() {
        return new ArrayList<>(items);
    }

    public MenuItem searchByName(String itemName) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null; // or throw an exception
    }

    public List<MenuItem> filterByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public List<MenuItem> viewItemsSortedByPrice() {
        return items.stream()
                .sorted(Comparator.comparingDouble(MenuItem::getPrice))
                .collect(Collectors.toList());
    }

    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(filename))) {
            for (MenuItem item : items) {
                writer.println(item.toString());
            }
            System.out.println("Menu saved successfully.");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        items.clear();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    items.add(MenuItem.fromString(line));
                }
            }
            System.out.println("Menu loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "items=" + items +
                '}';
    }
}
