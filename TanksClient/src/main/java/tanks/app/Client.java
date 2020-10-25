package tanks.app;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tanks.windows.ClientWindow;

public class Client extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tanks");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        loginWindow(primaryStage);

        primaryStage.show();
    }

    private void loginWindow(Stage primaryStage){
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

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e){
                ClientWindow window = new ClientWindow();
                window.start(serverIpField.getText(), Integer.parseInt(serverPortField.getText()));
            }
        };


        Button connect = new Button("Connect");
        connect.setDefaultButton(true);
        connect.setOnAction(event);

        grid.add(serverIpLabel, 0, 0);
        grid.add(serverIpField, 1, 0);
        grid.add(portLabel, 0, 1);
        grid.add(serverPortField, 1, 1);
        grid.add(connect, 1, 2);
        Scene primaryScene = new Scene(grid);

        primaryStage.setScene(primaryScene);
        primaryStage.show();

    }
}