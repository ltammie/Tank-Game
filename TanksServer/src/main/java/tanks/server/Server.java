package tanks.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

@Component
public class Server {
    private ServerSocket serverSocket;
    private Socket c1;
    private Socket c2;
    private DataInputStream in1;
    private DataOutputStream out1;
    private DataInputStream in2;
    private DataOutputStream out2;

    @Autowired
    StatRep  statRep;

    public void start(int port) throws IOException {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Server running!");
        } catch (IOException e) {
            System.err.println("Failed to initialize server!");
            System.err.println(e.getMessage());
            System.exit(-1);
        }
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
        int hp1 = 100;
        int hp2 = 100;
        int shot1 = 0;
        int shot2 = 0;

        int countShot1 = 0;
        int countShot2 = 0;

        int countHit1 = 0;
        int countHit2 = 0;


        while (true) {
            try {
                shift1 = in1.readInt();
                shot1 = in1.readInt();
                shift2 = in2.readInt();
                shot2 = in2.readInt();
            } catch (IOException e) {
                System.err.println("Failed to read from client");
                System.err.println(e.getMessage());
            }

            if (shot1 == 5) {
                bullets1.add(new Point(360 + (shift1 + 40), 500));
                countShot1++;
            }
            if (shot2 == 5) {
                bullets2.add(new Point(360 - shift2 + 40, 100));
                countShot2++;
            }

            Iterator<Point> i = bullets1.iterator();
            while (i.hasNext()) {
                Point p = i.next();
                p.y -= 10;
                if (p.y == 50) {
                    if (p.x > 360 - shift2 && p.x < 360 - shift2 + 80) {
                        hp2 -= 5;
                        i.remove();
                        countHit1++;
                    }
                }
                if (p.y < 0) {
                    i.remove();
                }
            }

            i = bullets2.iterator();
            while (i.hasNext()) {
                Point p = i.next();
                p.y += 10;
                if (p.y == 650) {
                    if (p.x > 360 + shift1 && p.x < 360 + shift1 + 80) {
                        hp1 -= 5;
                        countHit2++;
                        i.remove();
                    }
                }
                if (p.y > 800) {
                    i.remove();
                }
            }

            try {
                out1.writeInt(360 + shift1);
                out2.writeInt(360 + shift2);

                out1.writeInt(360 - shift2);
                out2.writeInt(360 - shift1);

                out1.writeInt(hp1);
                out2.writeInt(hp2);

                out1.writeInt(hp2);
                out2.writeInt(hp1);


                out1.writeInt(bullets1.size());
                for (Point p : bullets1) {
                    out1.writeInt(p.x);
                    out1.writeInt(p.y);
                }


                out1.writeInt(bullets2.size());
                for (Point p : bullets2) {
                    out1.writeInt(p.x);
                    out1.writeInt(p.y);
                }


                out2.writeInt(bullets2.size());
                for (Point p : bullets2) {
                    out2.writeInt(800 - p.x);
                    out2.writeInt(800 - p.y);
                }

                out2.writeInt(bullets1.size());
                for (Point p : bullets1) {
                    out2.writeInt(800 - p.x);
                    out2.writeInt(800 - p.y);
                }

                boolean status = hp1 <= 0 || hp2 <= 0;
                out1.writeBoolean(status);
                out2.writeBoolean(status);
                out1.flush();
                out2.flush();
                if (status) {
                    break;
                }
            } catch (IOException e) {
                System.err.println("Failed to send to client");
                System.err.println(e.getMessage());
            }
        }

        out1.writeInt(countShot1);
        out1.writeInt(countShot2);
        out1.writeInt(countHit1);
        out1.writeInt(countHit2);

        out2.writeInt(countShot2);
        out2.writeInt(countShot1);
        out2.writeInt(countHit2);
        out2.writeInt(countHit1);

        statRep.save(new Stat(1, countShot1, countHit1, countShot1 - countHit1));
        statRep.save(new Stat(2, countShot2, countHit2, countShot2 - countHit2));

        System.out.println("tt");
    }

}

