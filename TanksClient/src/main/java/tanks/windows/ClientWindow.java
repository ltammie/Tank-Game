package tanks.windows;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
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
        } catch (IOException e) {
            System.out.println("failed to connect!");
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
        Image tank = new Image("/images/player.png");
        Image enemy = new Image("/images/enemy.png");
        Image hp = new Image("/images/life.png");
        Image enemyHP = new Image("/images/life.png");
        Image borderHP = new Image("/images/border.png");
        Image enemyBorder = new Image("/images/border.png");

        AtomicInteger shift = new AtomicInteger(0);

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
                    }
                });


        gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(tank, 360, 650, 80, 100);
        primaryStage.show();
//
        while (!in.readBoolean()) ;
        System.out.println("connected");

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;
//        while (true) {

                int newPos;
                int enemyPos = 360;
                newPos = 360 + shift.get();

                try {
                    out.writeInt(shift.get());
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    enemyPos -= in.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int hpValue = 0;
                int enemyHpValue = 0;
                try {
                    hpValue = in.readInt();
                    enemyHpValue = in.readInt();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
                gc.drawImage(tank, newPos, 650, 80, 100);
                gc.drawImage(enemy, enemyPos, 30, 80, 100);
                gc.drawImage(hp, 10, 30, hpValue, 20);
                gc.drawImage(borderHP, 10, 30, 100, 20);
                gc.drawImage(enemyHP, 10, 30, enemyHpValue, 20);
                gc.drawImage(enemyBorder, 10, 30, 10, 20);

            }
        }.start();
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

    private static class ReadMsg extends Thread {
//
//        @Override
//        public void run() {
//            String str;
//            try {
//                while (true) {
//                    str = read();
//                    if (str.equals("Exit")) {
//                        ClientWindow.stopThread();
//                        break;
//                    }
//                    System.out.println(str);
//                }
//            } catch (IOException e) {
//                ClientWindow.stopThread();
//            }
//        }
    }

    public static class WriteMsg extends Thread {

//        @Override
//        public void run() {
//            while (true) {
//                String userWord;
//                try {
//                    userWord = scanner.nextLine();
//                    if (userWord.equals("Exit")) {
//                        send("Exit");
//                        Client.stopThread();
//                        break;
//                    } else {
//                        send(userWord);
//                    }
//                } catch (IOException e) {
//                    Client.stopThread();
//                }
//            }
//        }
    }
}


