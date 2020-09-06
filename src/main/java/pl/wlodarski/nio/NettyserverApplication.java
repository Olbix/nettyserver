package pl.wlodarski.nio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wlodarski.nio.components.EyetrackerServer;

@SpringBootApplication
public class NettyserverApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(NettyserverApplication.class, args);
    }

    @Override
    public void run(String... args) {
        String host = null;
        Integer port = null;
        if (args.length > 0) {
            host = args[0];
            if (args.length > 1) {
                port = Integer.getInteger(args[1]);
            }
        }
        if (host == null) {
            host = "127.0.0.1";
        }
        if (port == null) {
            port = 9099;
        }
        EyetrackerServer eyetrackerServer = new EyetrackerServer(host, port);
        eyetrackerServer.runServer();
    }
}
