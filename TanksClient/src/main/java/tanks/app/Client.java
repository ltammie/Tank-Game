package tanks.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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

        Label serverIpLabel = new Label("IP-adress");
        TextField serverIpField = new TextField();
        serverIpField.setTooltip(new Tooltip("Enter server ip-address"));

        Label portLabel = new Label("Port");
        TextField serverPortField = new TextField();
        serverPortField.setTooltip(new Tooltip("Enter servers port to connect"));

        Button connect = new Button("Connect");

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

//
//
//@Parameters(separators = "=")
//class Console{
//    @Parameter(names = {"--server-port", "-p"}, description = "port")
//    private Integer port = 8081;
//
//    @Parameter(names = {"--server-ip", "-ip"}, description = "server-ip")
//    private String ip = "localhost";
//
//    public Integer getPort() {
//        return port;
//    }
//
//    public String getIp() {
//        return ip;
//    }
//}
//
//public class Client {
//
//    private static Socket client;
//    private static BufferedReader in;
//    private static BufferedWriter out;
//    private static final Scanner scanner = new Scanner(System.in);
//
//    public static void main(String[] args) throws IOException {
//        Console console = new Console();
//        JCommander.newBuilder().addObject(console).build().parse(args);
//
//        try {
//            client = new Socket(console.getIp(), console.getPort());
//        } catch (IOException e) {
//            System.out.println("failed to connect!");
//        }
//        try {
//            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
//            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
//            System.out.println(read());
//            while (!read().equals("yes")){
//                send(scanner.nextLine());
//            }
//            System.out.println(read());
//            send(scanner.nextLine());
//            System.out.println(read());
//            send(scanner.nextLine());
//            System.out.println(read());
//            new ReadMsg().start();
//            new WriteMsg().start();
//        } catch (IOException e) {
//            System.err.println(e.toString());
//            Client.stopThread();
//        }
//    }
//
//    private static void stopThread() {
//        try {
//            if (!client.isClosed()) {
//                client.close();
//                in.close();
//                out.close();
//            }
//        } catch (IOException ignored) {}
//    }
//
//    private static class ReadMsg extends Thread {
//
//        @Override
//        public void run() {
//            String str;
//            try {
//                while (true) {
//                    str = read();
//                    if (str.equals("Exit")) {
//                        Client.stopThread();
//                        break;
//                    }
//                    System.out.println(str);
//                }
//            } catch (IOException e) {
//                Client.stopThread();
//            }
//        }
//    }
//    public static class WriteMsg extends Thread {
//
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
//    }
//
//
//    public static String read() throws IOException {
//        return in.readLine();
//    }
//
//    public  static void send(String message) throws IOException {
//        out.write(message + "\n");
//        out.flush();
//    }
//}
