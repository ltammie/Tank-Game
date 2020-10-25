package tanks.server;

import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public Socket client;
    public int id;
    public boolean isPlaying;

    public ServerThread(Socket client, int id) throws IOException {
        this.client = client;
        in = new ObjectInputStream(client.getInputStream());
        out = new ObjectOutputStream(client.getOutputStream());

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
                PackageToServer ps = (PackageToServer) in.readObject();

                System.out.println(id);
                System.out.println(ps.shiftX);
                System.out.println(ps.isShot);



//                PackageToClient pc = new PackageToClient(ps.shiftX, hp, enemyHp);
//                for (ServerThread s : Server.serverList){
//                    if (s == this) {
//                        continue;
//                    }
//                    s.out.writeObject(pc);
//                    s.out.flush();
//                }
            }
        } catch (IOException | ClassNotFoundException ignored) {
        }
        stopThread();
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
