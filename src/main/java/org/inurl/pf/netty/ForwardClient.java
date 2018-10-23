package org.inurl.pf.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.inurl.pf.model.Router;

class ForwardClient {

    private Router router;
    private String remoteAddress;

    ForwardClient(Router router, String remoteAddress) {
        this.router = router;
        this.remoteAddress = remoteAddress;
    }

    void init() {

        Bootstrap b = new Bootstrap();
        b.group(NettyGroup.clientGroup);//NioEventLoopGroup
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline()
                .addLast(new ForwardClientInboundHandler(remoteAddress))
                .addLast(new ForwardClientOutboundHandler())
                ;
            }
        });

        ChannelFuture f = b.connect(router.getTargetHost(), router.getTargetPort());
        f.channel().closeFuture();

    }

}
