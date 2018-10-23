package org.inurl.pf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ForwardClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardClientInboundHandler.class);

    private String remoteAddress;

    ForwardClientInboundHandler(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        Channel sc = ChannelPool.getServerChannel(remoteAddress);
        buf.readBytes(bytes);
        sc.write(bytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ChannelPool.flushServer(remoteAddress);
    }

    /**
     * 断开触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String ra = ctx.channel().remoteAddress().toString();
        logger.info("目标服务器断开 : " + ra);
        ChannelPool.releaseChannel(ra);
    }

    /**
     * 传入触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        String ra = ctx.channel().remoteAddress().toString();
        logger.info("目标服务器连接 : " + ra);
        ChannelPool.setClientChannel(remoteAddress, ctx.channel());
        Lock.Connection.release(remoteAddress);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}