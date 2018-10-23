package org.inurl.pf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

public class ForwardServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardServerInboundHandler.class);

    private Router router;

    ForwardServerInboundHandler(Router router) {
        this.router = router;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String ra = ctx.channel().remoteAddress().toString();
        Lock.Connection.wait(ra);
        Channel cc = ChannelPool.getClientChannel(ctx.channel().remoteAddress().toString());
        cc.write(bytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ChannelPool.flushClient(ctx.channel().remoteAddress().toString());
    }

    /**
     * 断开触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("客户端断开 : " + ctx.channel().remoteAddress());
        String ra = ctx.channel().remoteAddress().toString();
        ChannelPool.releaseChannel(ra);
    }

    /**
     * 传入触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String ra = ctx.channel().remoteAddress().toString();
        Lock.Connection.init(ra);
        logger.info("客户端连接 : " + ra);
        ChannelPool.setServerChannel(ra, ctx.channel());
        new ForwardClient(router, ra).init();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}