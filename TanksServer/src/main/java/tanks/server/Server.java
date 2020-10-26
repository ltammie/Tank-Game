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
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server running!");
        } catch (IOException e) {
            System.err.println("Failed to initialize server!");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void start() {
        try {
            System.out.println("Waiting for player one!");
            c1 = serverSocket.accept();
            System.out.println("Player one connected!");
            System.out.println("Waiting for player two!");
            c2 = serverSocket.accept();
            System.out.println("Player two connected!");
        } catch (IOException e) {
            System.err.println("Failed two connect one of the players!");
            System.err.println(e.getMessage());
            System.exit(-1);
        }

        try {
            out1 = new DataOutputStream(c1.getOutputStream());
            in1 = new DataInputStream(c1.getInputStream());

            out2 = new DataOutputStream(c2.getOutputStream());
            in2 = new DataInputStream(c2.getInputStream());
        } catch (IOException e) {
            System.err.println("Failed two init players data streams!");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        LinkedList<Point> bullets1 = new LinkedList<>();
        LinkedList<Point> bullets2 = new LinkedList<>();

        int shift1 = 0;
        int shift2 = 0;
        int hp1 = 0;
        int hp2 = 0;

        while (true) {

            try {
                shift1 = in1.readInt();
                shift2 = in2.readInt();
            } catch (IOException e) {
                System.err.println("Failed to read from client");
                System.err.println(e.getMessage());
            }

            try {
                out1.writeInt(360 + shift1);
                out1.writeInt(360 - shift2);
                out1.writeInt(hp1);
                out1.writeInt(hp2);

                out2.writeInt(360 + shift2);
                out2.writeInt(360 - shift1);
                out2.writeInt(hp2);
                out2.writeInt(hp1);



                out1.flush();
                out2.flush();
            } catch (IOException e) {
                System.err.println("Failed to send to client");
                System.err.println(e.getMessage());
            }
        }
    }
}

