package org.inurl.pf.netty;

import org.inurl.pf.model.Router;
import org.inurl.pf.support.NetUtil;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author raylax
 * @date 2019/1/4
 */
public class ForwardServerTest {

    @Test
    public void start() {
        int localPort = 12345;
        Router router = new Router(localPort, "0.0.0.0", 3306, "127.0.0.1");
        Server server = new ForwardServer(router);
        server.start();
        assertFalse(NetUtil.portCheck(localPort));
    }

    @Test
    public void stop() {
        int localPort = 12345;
        Router router = new Router(localPort, "0.0.0.0", 3306, "127.0.0.1");
        Server server = new ForwardServer(router);
        server.start();
        server.stop();
        assertTrue(NetUtil.portCheck(localPort));
    }

}