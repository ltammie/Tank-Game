package tanks.windows;

import javafx.scene.Group;
import javafx.scene.Scene;
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
        Group root = new Group();
        root.getChildren().add(GameWindow.getGameCanvas());
        primaryStage.setScene(new Scene(root));

        try {
            client = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println("failed to connect!");
        }

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            System.out.println(read());
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

