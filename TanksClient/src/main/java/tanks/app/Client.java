package tanks.app;

import javafx.application.Application;

import javafx.scene.Scene;

import javafx.stage.Stage;
import tanks.windows.Menu;

public class Client extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Connection Window");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setScene(new Scene(Menu.getMenuGrid(primaryStage)));
        primaryStage.show();
    }
}