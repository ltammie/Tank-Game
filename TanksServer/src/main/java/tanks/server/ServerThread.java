package tanks.server;


import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    public DataInputStream in;
    public DataOutputStream out;
    public Socket client;
    public int id;

    public ServerThread(Socket client, int id) throws IOException {
        this.client = client;
        in = new DataInputStream(client.getInputStream());
        out = new DataOutputStream(client.getOutputStream());
        this.id = id;
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            int playerPos = 0;
            while (true) {
                playerPos = in.readInt();
                for (ServerThread s : Server.serverList){
                    if (s == this) {
                        continue;
                    }
                    s.out.write(playerPos);
                    s.out.flush();
                }
            }
        } catch (IOException ignored) {
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
