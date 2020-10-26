package tanks.app;

import com.beust.jcommander.JCommander;
import tanks.server.Server;

import java.io.IOException;

public class TankServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments console = new ConsoleArguments();
        JCommander.newBuilder().addObject(console).build().parse(args);

        Server server = new Server(console.getPort());
        server.start();
    }
}
