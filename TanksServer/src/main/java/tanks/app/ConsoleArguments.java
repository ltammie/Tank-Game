package tanks.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
class ConsoleArguments{
    @Parameter(names = {"--port", "-p"}, description = "port")
    private Integer port = 8081;

    public Integer getPort() {
        return port;
    }
}
