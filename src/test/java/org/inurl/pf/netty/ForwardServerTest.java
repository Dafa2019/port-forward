package org.inurl.pf.netty;

import org.inurl.pf.model.Router;
import org.junit.Test;

import static org.junit.Assert.*;

public class ForwardServerTest {

    @Test
    public void run() throws Exception {

        ForwardServer server = new ForwardServer(new Router(13306, "0.0.0.0", 3306, "127.0.0.1"));
        server.start();

        server.join();

    }
}