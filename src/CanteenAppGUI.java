import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CanteenAppGUI extends Application {
    private Menu menu;
    private List_of_Orders listOfOrders;

    @Override
    public void start(Stage primaryStage) {
        // Initialize menu and orders
        menu = new Menu();
        listOfOrders = new List_of_Orders();
        menu.loadFromFile("menu.txt");
        listOfOrders.loadFromFile("orders.txt");

        // Menu Screen
        ListView<String> menuListView = new ListView<>();
        for (MenuItem item : menu.viewAllItems()) {
            menuListView.getItems().add(item.toString());
        }
        Button toOrdersButton = new Button("View Pending Orders");
        Label menuHeader = new Label("Menu");
        menuHeader.getStyleClass().add("header");
        VBox menuBox = new VBox(menuHeader, menuListView, toOrdersButton);
        BorderPane menuPane = new BorderPane();
        menuPane.setCenter(menuBox);
        Scene menuScene = new Scene(menuPane, 500, 400);
        menuScene.getStylesheets().add("styles.css");

        // Orders Screen
        ListView<String> ordersListView = new ListView<>();
        for (Order order : listOfOrders.getPendingOrders()) {
            ordersListView.getItems().add(order.toString());
        }
        Button toMenuButton = new Button("Back to Menu");
        Label ordersHeader = new Label("Pending Orders");
        ordersHeader.getStyleClass().add("header");
        VBox ordersBox = new VBox(ordersHeader, ordersListView, toMenuButton);
        BorderPane ordersPane = new BorderPane();
        ordersPane.setCenter(ordersBox);
        Scene ordersScene = new Scene(ordersPane, 500, 400);
        ordersScene.getStylesheets().add("styles.css");

        // Navigation
        toOrdersButton.setOnAction(e -> primaryStage.setScene(ordersScene));
        toMenuButton.setOnAction(e -> primaryStage.setScene(menuScene));

        // Set initial scene
        primaryStage.setTitle("Canteen App GUI");
        primaryStage.setScene(menuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
