package tanks.windows;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientWindow {
    private static Socket client;
    private static BufferedReader in;
    private static BufferedWriter out;
    private Stage primaryStage;

    public ClientWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    public void start(String ip, int port){

        primaryStage.setTitle("Game");
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setResizable(false);

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image field = new Image( "/images/field.png" );
        Image tank = new Image( "/images/player.png" );


        gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(tank, 360, 650, 80, 100);
        primaryStage.show();

        try {
            client = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("failed to connect!");
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println(read());
            final long startNanoTime = System.nanoTime();

            new AnimationTimer()
            {
                public void handle(long currentNanoTime)
                {


                    double t = (currentNanoTime - startNanoTime) / 1000000000.0;
                    double x = 232 + 128 * Math.cos(t);

                    gc.drawImage(field, 0, 0, canvas.getWidth(), canvas.getHeight());
                    gc.drawImage(tank, x, 650, 80, 100);
                }
            }.start();
        } catch (IOException e) {
            System.err.println(e.toString());
            ClientWindow.stopThread();
        }
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

        @Override
        public void run() {
            String str;
            try {
                while (true) {
                    str = read();
                    if (str.equals("Exit")) {
                        ClientWindow.stopThread();
                        break;
                    }
                    System.out.println(str);
                }
            } catch (IOException e) {
                ClientWindow.stopThread();
            }
        }
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


    public static String read() throws IOException {
        return in.readLine();
    }

    public static void send(String message) throws IOException {
        out.write(message + "\n");
        out.flush();
    }
}

