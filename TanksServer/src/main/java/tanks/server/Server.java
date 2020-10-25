package tanks.server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    private ServerSocket serverSocket;
    private Socket c1;
    private Socket c2;
    private DataInputStream in1;
    private DataOutputStream out1;
    private DataInputStream in2;
    private DataOutputStream out2;

    public Server(int port) {
        try {
            this.serverSocket = ServerSocket(port);
            System.out.println("Server running!");
        } catch (IOException e) {
            System.out.println("Failed to initialize server!");
            System.exit(-1);
        }
    }

    public void start(){
       try {
           c1 = serverSocket.accept();
           System.out.println("Player one connected!");
           c2 = serverSocket.accept();
           System.out.println("Player two connected!");
       } catch (IOException e) {
           System.out.println("Failed two connect one of the players!");
           System.exit(-1);
       }

       try {
           out1 = new DataOutputStream(c1.getOutputStream());
           in1 = new DataInputStream(c1.getInputStream());

           out2 = new DataOutputStream(c2.getOutputStream());
           in2 = new DataInputStream(c2.getInputStream());
       } catch (IOException e) {
           System.out.println("Failed two init players data streams!");
           System.exit(-1);
       }
       LinkedList<Point> bullets1 = new LinkedList<>();
       LinkedList<Point> bullets2 = new LinkedList<>();
       while (true) {




       }
    }
}

