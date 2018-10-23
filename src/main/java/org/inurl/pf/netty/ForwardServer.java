package org.inurl.pf.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

public class ForwardServer extends Thread {

    private static Log logger = LogFactory.getLog(ForwardServer.class);

    private Router router;

    public ForwardServer(Router router) {
        this.router = router;
    }

    @Override
    public void run() {

        EventLoopGroup parentGroup = NettyGroup.childGroup;
        EventLoopGroup childGroup = NettyGroup.parentGroup;
        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline()
                .addLast(new ForwardServerInboundHandler(router))
                .addLast(new ForwardServerOutboundHandler(router))
                ;
            }
        })
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        ;

        ChannelFuture f = null;
        try {
            logger.info("启动端口转发 : " + router);
            f = b.bind(router.getPort()).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException ignored) {
            // thread exit
        }
        if (f != null)
            f.channel().close();
        logger.info("停止端口转发 : " + router);
    }
}
