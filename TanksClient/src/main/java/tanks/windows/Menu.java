package tanks.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Menu {

    public static GridPane getMenuGrid(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Label serverIpLabel = new Label("IP-address");
        TextField serverIpField = new TextField();
        serverIpField.setTooltip(new Tooltip("Enter server ip-address"));

        Label portLabel = new Label("Port");
        TextField serverPortField = new TextField();
        serverPortField.setTooltip(new Tooltip("Enter servers port to connect"));

        EventHandler<ActionEvent> event = e -> {
            ClientWindow window = new ClientWindow(primaryStage);
            window.start(serverIpField.getText(), Integer.parseInt(serverPortField.getText()));
        };

        Button connect = new Button("Connect");
        connect.setOnAction(event);
        grid.add(serverIpLabel, 0, 0);
        grid.add(serverIpField, 1, 0);
        grid.add(portLabel, 0, 1);
        grid.add(serverPortField, 1, 1);
        grid.add(connect, 1, 2);

        return grid;
    }

}
