package org.inurl.pf.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

/**
 * @author raylax
 */
class ForwardClient implements Client {

    private static Log logger = LogFactory.getLog(ForwardClient.class);

    private final Router router;
    private final Channel serverChannel;
    private Channel channel;

    ForwardClient(Router router, Channel serverChannel) {
        this.router = router;
        this.serverChannel = serverChannel;
    }

    @Override
    public void connect() {

        Bootstrap b = new Bootstrap();
        b.group(NettyGroup.getClientGroup());
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(new ForwardClientInboundHandler(serverChannel));
            }
        });
        ChannelFuture f = b.connect(router.getTargetHost(), router.getTargetPort()).syncUninterruptibly();
        channel = f.channel();
        logger.info("连接上游 " + router.getTargetHost() + ":" + router.getTargetPort());

    }

    @Override
    public void disconnect() {
        channel.close().syncUninterruptibly();
        logger.info("断开上游 " + router.getTargetHost() + ":" + router.getTargetPort());
    }

    @Override
    public void write(Object msg) {
        channel.write(msg);
    }

    @Override
    public void flush() {
        channel.flush();
    }

}
