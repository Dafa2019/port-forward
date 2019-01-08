package org.inurl.pf.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.inurl.pf.model.Router;
import org.inurl.pf.support.TestUpServer;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author raylax
 * @date 2019/1/8
 */
public class ForwardServerInboundHandlerTest {

    private TestUpServer upServer;
    private EmbeddedChannel channel;

    @Before
    public void setup() {
        Vector<Client> clients = new Vector<>();
        int localPort = 12345;
        int upPort = 13306;
        Router router = new Router(localPort, "0.0.0.0", upPort, "127.0.0.1");
        upServer = new TestUpServer(router);
        channel = new EmbeddedChannel(new ForwardServerInboundHandler(router, clients));
    }

    @Test
    public void channelRead() throws Exception {
        byte[] bytes = new byte[] {
                (byte) 0xaa,
                (byte) 0xbb,
                (byte) 0xcc
        };
        channel.writeInbound(Unpooled.wrappedBuffer(bytes));
        channel.flush();
        TimeUnit.SECONDS.sleep(1);
        byte[] result = new byte[bytes.length];
        upServer.read(result);
        assertArrayEquals(bytes, result);
        upServer.stop();
    }

    @Test
    public void channelInactive() throws Exception {
        channel.close();
        TimeUnit.SECONDS.sleep(1);
        assertTrue(upServer.isClosed());
        upServer.stop();
    }

    @Test
    public void channelActive() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        assertTrue(upServer.isConnected());
        upServer.stop();
    }

}