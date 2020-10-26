package tanks.app;

import com.beust.jcommander.JCommander;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tanks.config.Config;
import tanks.server.Server;

import java.io.IOException;

public class TankServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments console = new ConsoleArguments();
        JCommander.newBuilder().addObject(console).build().parse(args);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        server.start(console.getPort());
    }
}
