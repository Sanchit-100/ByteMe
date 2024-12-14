public class MenuItem {
    private String name;
    private double price;
    private String category;
    private boolean available;

    public MenuItem(String name, double price, String category, boolean available) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    // Getter and setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter for price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Getter and setter for category
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // Getter and setter for availability
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }


    @Override
    public String toString() {
        return "MenuItem{" +
                "name=\"" + name + "\"," +
                " price=" + price +
                ", category=\"" + category + "\"," +
                " available=" + available +
                '}';
    }


    public static MenuItem fromString(String data) {
//        System.out.println(data);
        String[] parts = data.replace("MenuItem{", "").replace("}", "").split(", ");
//        System.out.println(parts.length);
        String name = parts[0].split("=")[1].replace("\"", "");
        double price = Double.parseDouble(parts[1].split("=")[1]);
        String category = parts[2].split("=")[1].replace("\"", "");
        boolean available = Boolean.parseBoolean(parts[3].split("=")[1]);
        return new MenuItem(name, price, category, available);
    }

}
