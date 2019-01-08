package org.inurl.pf.netty;

import org.inurl.pf.model.Router;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author raylax
 * @date 2019/1/8
 */
public class ForwardClientTest {

    @Test
    public void connect() {
        int localPort = 12345;
        Router router = new Router(localPort, "0.0.0.0", 3306, "127.0.0.1");
        Client client = new ForwardClient(router, null);
        client.connect();

    }

    @Test
    public void disconnect() {
        int localPort = 12345;
        Router router = new Router(localPort, "0.0.0.0", 3306, "127.0.0.1");
        Client client = new ForwardClient(router, null);
        client.connect();
        client.disconnect();
    }

}