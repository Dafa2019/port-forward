package org.inurl.pf.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

import java.util.Vector;

/**
 * @author raylax
 */
public class ForwardServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardServerInboundHandler.class);

    private final Router router;
    private Client client;
    private final Vector<Client> clients;

    ForwardServerInboundHandler(Router router, Vector<Client> clients) {
        this.router = router;
        this.clients = clients;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        client.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        client.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        client.disconnect();
        clients.remove(client);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("客户端连入 " + ctx.channel().remoteAddress());
        client = new ForwardClient(router, ctx.channel());
        client.connect();
        clients.add(client);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("客户端异常", cause);
        ctx.close();
    }

}