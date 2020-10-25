package tanks.server;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class ServerThread extends Thread {
    public DataInputStream in;
    public DataOutputStream out;
    public Socket client;
    public int id;
    public boolean isPlaying;
    public LinkedList<Point> bullets = new LinkedList<>();
    public LinkedList<Point> enemyBulls = new LinkedList<>();

    public ServerThread(Socket client, int id) throws IOException {
        this.client = client;
        out = new DataOutputStream(client.getOutputStream());
        in = new DataInputStream(client.getInputStream());
        isPlaying = false;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            int playerPos = 0;
            int hp = 100;
            int enemyHp = 100;
            while (true) {
                int shift = in.readInt();
                boolean shot = in.readBoolean();
                System.out.println(shot);
                if (shot) {
                    bullets.add(new Point(360 + shift, 630));
                    enemyBulls.add(new Point(360 - shift, 50));
                }

                Iterator<Point> i = bullets.iterator();
                while (i.hasNext()) {
                    Point p = i.next(); // must be called before you can call i.remove()
                    p.y -= 10;
                    if (p.y < 0) {
                        i.remove();
                    }
                }


                i = enemyBulls.iterator();
                while (i.hasNext()) {
                    Point p = i.next(); // must be called before you can call i.remove()
                    p.y += 20;
                    if (p.y == 650) {
                        if (p.x == 360 + shift) {
                            hp -= 5;
                        }
                    }
                    if (p.y > 800) {
                        i.remove();
                    }
                }

                for (ServerThread s : Server.serverList){
                    s.out.writeInt(shift);
                    s.out.writeInt(hp);
                    s.out.writeInt(enemyHp);
                    s.out.writeInt(bullets.size());
                    for (Point p: bullets) {
                        s.out.writeInt(p.x);
                        s.out.writeInt(p.y);
                    }
                    s.out.writeInt(enemyBulls.size());
                    for (Point p: enemyBulls) {
                        s.out.writeInt(p.x);
                        s.out.writeInt(p.y);
                    }
                    s.out.flush();
                }
            }
        } catch (IOException e) {
        }
    }

    public void stopThread() {
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
