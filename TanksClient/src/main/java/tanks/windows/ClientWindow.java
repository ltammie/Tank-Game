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


    public void start(String ip, int port) throws IOException, ClassNotFoundException {

        try {
            client = new Socket(ip, port);
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
            System.out.println("Connected?");
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

        Image bullet = new Image("/images/playerBullet.png");
        Image eBullet = new Image("/images/enemyBullet.png");

        Image boom = new Image("/images/fail.png");

        AtomicInteger shift = new AtomicInteger(0);
        AtomicBoolean isShot = new AtomicBoolean(false);

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
                        if (!isShot.get()) {
                            isShot.set(true);
                        }
                    }
                });


        gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(tank, 360, 650, 80, 100);
        primaryStage.show();

        while (true) {
            if (in.readBoolean())
                break;
        }
        System.out.println("connected");

        final long startNanoTime = System.nanoTime();

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());

                int newPos;
                int enemyPos = 360;
                newPos = 360 + shift.get();

                try {
                    out.writeInt(shift.get());
                    out.writeBoolean(isShot.get());
                    out.flush();
                    isShot.set(false);
                } catch (IOException e) {
                }

                int hpValue = 100;
                int enemyHpValue = 100;
                int b = 0;
                int eb = 0;

                try {
                    boolean flag = in.readBoolean();
                    if (flag) {
                        int tmp = 0;
                        tmp -= in.readInt();
                        tmp = in.readInt();
                        tmp = in.readInt();
                        b = in.readInt();
                        for (int i = 0; i < b; i++) {
                            int x = in.readInt();
                            int y = in.readInt();
                            if (x == tmp && y == 30) {
                                tmp -= 5;
                            }
                        }

                        eb = in.readInt();
                        for (int i = 0; i < eb; i++) {
                            int x = in.readInt();
                            int y = in.readInt();
                        }
                    } else {
                        enemyPos -= in.readInt();
                        hpValue = in.readInt();
                        enemyHpValue = in.readInt();
                        b = in.readInt();
                        for (int i = 0; i < b; i++) {
                            int x = in.readInt();
                            int y = in.readInt();
                            if (x == enemyPos && y == 30) {
                                enemyHpValue -= 5;
                            }
                            gc.drawImage(bullet, x, y, 20, 20);
                        }

                        eb = in.readInt();
                        for (int i = 0; i < eb; i++) {
                            int x = in.readInt();
                            int y = in.readInt();
                            gc.drawImage(eBullet, x, y, 20, 20);
                        }
                    }
                } catch (IOException e) {
                }





                gc.drawImage(tank, newPos, 650, 80, 100);
                gc.drawImage(enemy, enemyPos, 30, 80, 100);

                gc.drawImage(hp, 10, 750, hpValue, 20);
                gc.drawImage(borderHP, 10, 750, 110, 30);

                gc.drawImage(enemyHP, 650, 15, enemyHpValue, 20);
                gc.drawImage(enemyBorder, 650, 15, 110, 30);

                if (enemyHpValue <= 0) {
                    gc.drawImage(boom, enemyPos, 30, 80, 100);
                }
                if (hpValue <= 0) {
                    gc.drawImage(boom, newPos, 650, 80, 100);
                }

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


