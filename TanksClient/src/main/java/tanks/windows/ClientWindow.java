package tanks.windows;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ClientWindow {
    private static Socket client;
    private static DataInputStream in;
    private static DataOutputStream out;
    private Stage primaryStage;

    public ClientWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    public void start(String ip, int port) throws IOException {

        try {
            client = new Socket(ip, port);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            System.out.println("Connected?");
        } catch (IOException e) {
            System.err.println("Failed to connect!");
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        primaryStage.setTitle("Game");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);
        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);
        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image field = new Image("/images/field.png");
        Image player = new Image("/images/player.png");
        Image enemy = new Image("/images/enemy.png");
        Image hp = new Image("/images/life.png");
        Image enemyHp = new Image("/images/life.png");
        Image border = new Image("/images/border.png");
        Image bullet = new Image("/images/playerBullet.png");
        Image eBullet = new Image("/images/enemyBullet.png");
        Image boom = new Image("/images/fail.png");

        AtomicInteger shift = new AtomicInteger(0);
        AtomicInteger isShot = new AtomicInteger(0);
        AtomicBoolean status = new AtomicBoolean(false);

        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();

                    if (code.equals("RIGHT")) {
                        shift.getAndAdd(5);
                        shift.set(Math.min(shift.get(), 350));
                    }
                    if (code.equals("LEFT")) {
                        shift.getAndAdd(-5);
                        shift.set(Math.max(shift.get(), -350));
                    }
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    if (code.equals("ESCAPE")) {
                        ClientWindow.stopThread();
                        primaryStage.close();
                    }
                    if (code.equals("SPACE")) {
//                        if (isShot.get() <= 0) {
                            isShot.set(5);
//                        }
                    }
                });

        gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(player, 360, 650, 80, 100);
        primaryStage.show();

        System.out.println("connected");

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());

                int newPos = 360;
                int enemyPos = 360;
                int hpValue = 100;
                int enHpValue = 100;

                try {
                    out.writeInt(shift.get());
                    out.writeInt(isShot.get());
                    out.flush();
                } catch (IOException e) {
                    System.err.println("Failed to send to server!");
                    System.err.println(e.getMessage());
                }

                try {
                    newPos = in.readInt();
                    enemyPos = in.readInt();
                    hpValue = in.readInt();
                    enHpValue = in.readInt();
                    int b = 0;
                    int eb = 0;
                    b = in.readInt();
                    for (int i = 0; i < b; i++){
                        int x = in.readInt();
                        int y = in.readInt();
                        gc.drawImage(bullet, x, y, 10, 10);
                    }
                    eb = in.readInt();
                    for (int i = 0; i < eb; i++){
                        int x = in.readInt();
                        int y = in.readInt();
                        gc.drawImage(eBullet, x, y, 10, 10);
                    }
                    status.set(in.readBoolean());

                } catch (IOException e) {
                    System.err.println("Failed to read from server!");
                    System.err.println(e.getMessage());
                }
                isShot.set(isShot.get() - 1);

                gc.drawImage(player, newPos, 650, 80, 100);
                gc.drawImage(enemy, enemyPos, 30, 80, 100);

                gc.drawImage(hp, 15, 740, hpValue, 20);
                gc.drawImage(border, 10, 730, 120, 40);

                gc.drawImage(enemyHp, 650, 15, enHpValue, 20);
                gc.drawImage(border, 640, 10, 120, 49);

                if (status.get()) {
                    if (hpValue <= 0) {
                        gc.drawImage(boom, newPos, 650, 80, 100);
                    }
                    if (enHpValue <= 0) {
                        gc.drawImage(boom, enemyPos, 30, 80, 100);
                    }
                    this.stop();
                }
            }
        }.start();

        System.out.println(status.get());
        showAlertWithHeaderText();


        if (status.get()) {
//            int shots1 = in.readInt();
//            int hit1 = in.readInt();
//            int shots2 = in.readInt();
//            int hit2 = in.readInt();
//
//            int mis1 = shots1 - hit1;
//            int mis2 = shots2 - hit2;


        }

    }


    private void showAlertWithHeaderText() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Test Connection");
        alert.setHeaderText("Results:");
        alert.setContentText("Connect to the database successfully!");

        alert.showAndWait();
    }

    private static void stopThread() {
        try {
            if (!client.isClosed()) {
                client.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

}


