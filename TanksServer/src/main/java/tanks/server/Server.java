package tanks.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    public static LinkedList<ServerThread> serverList = new LinkedList<>();
    public static int[] player = new int[2];


    public static void start(Integer port) throws IOException, InterruptedException {
        try {
            server = new ServerSocket(port);
            System.out.println("Server running!");
            for (int i = 1; i <= 2; i++) {
                System.out.println("Waiting for player " + i);
                clientSocket = server.accept();
                try {
                    serverList.add(new ServerThread(clientSocket, i));
                    System.out.println("Added player " + i);
                } catch (IOException ignored) {
                    clientSocket.close();
                }
            }
            for (ServerThread t : serverList) {
                t.out.writeBoolean(true);
                t.start();
            }



        } finally {
            for (ServerThread t : serverList) {
                t.join();
            }
            server.close();
            System.out.println("Server stopped working");
        }
    }
}

