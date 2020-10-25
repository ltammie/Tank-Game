package tanks.server;


import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread {
    private BufferedReader in;
    private BufferedWriter out;
    private Socket client;

    public ServerThread(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        start();
    }

    @Override
    public void run() {
        String word;
        try {
            send("Start messaging");
            while (true) {
                word = read();
                if (word.equals("Exit")) {
                    break;
                }
                System.out.println(word);
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

    public String read() throws IOException {
        String text = in.readLine();
        if (text == null) {
            return "";
        }
        return text;
    }

    public void send(String message) throws IOException {
        try {
            out.write(message + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
