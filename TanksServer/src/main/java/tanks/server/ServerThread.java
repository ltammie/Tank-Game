package tanks.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread {
    public DataInputStream in;
    public DataOutputStream out;
    public Socket client;
    public int id;
    public boolean isPlaying;
    public ArrayList<Integer> bullets =  new ArrayList<Integer>();

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
            int enemyHp = 70;
            while (true) {
                int shift = in.readInt();
                boolean shot = in.readBoolean();





                for (ServerThread s : Server.serverList){
                    if (s == this) {
                        continue;
                    }
                    s.out.writeInt(shift);
                    s.out.writeInt(hp);
                    s.out.writeInt(enemyHp);
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
