package tanks.app;

import com.beust.jcommander.JCommander;
import tanks.server.Server;

import java.io.IOException;

public class TankServer {

    public static void main(String[] args) {
        ConsoleArguments console = new ConsoleArguments();
        JCommander.newBuilder().addObject(console).build().parse(args);

        try {
            Server.start(console.getPort());
        } catch (IOException | InterruptedException e) {
            System.out.println("Server could not run.");
            e.printStackTrace();
        }
    }

}
