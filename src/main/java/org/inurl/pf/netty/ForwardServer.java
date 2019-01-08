package org.inurl.pf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;
import org.inurl.pf.support.FlowAnalysisUtil;

import java.util.Vector;

/**
 * @author raylax
 */
public class ForwardServer implements Server {

    private static Log logger = LogFactory.getLog(ForwardServer.class);
    private final Router router;
    private ChannelFuture channelFuture;
    private final Vector<Client> clients = new Vector<>();

    public ForwardServer(Router router) {
        this.router = router;
    }

    @Override
    public void start() {

        EventLoopGroup parentGroup = NettyGroup.getParentGroup();
        EventLoopGroup childGroup = NettyGroup.getChildGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new ForwardServerInboundHandler(router, clients));
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);
        channelFuture = b.bind(router.getPort()).syncUninterruptibly();
        logger.info("启动转发 " + router);

    }

    @Override
    public void stop() {
        for (Client client : clients) {
            client.disconnect();
        }
        channelFuture.channel().close().syncUninterruptibly();
        logger.info("停止转发 " + router);
    }
}
