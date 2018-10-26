package org.inurl.pf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.FlowAnalysis;
import org.inurl.pf.model.Router;
import org.inurl.pf.support.FlowAnalysisUtil;

public class ForwardClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardClientInboundHandler.class);

    private String remoteAddress;

    private Router router;

    ForwardClientInboundHandler(Router router, String remoteAddress) {
        this.router = router;
        this.remoteAddress = remoteAddress;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int len = buf.readableBytes();
        FlowAnalysisUtil.addReviveFlow(router.getNo(), len);
        byte[] bytes = new byte[len];
        Channel sc = ChannelPool.getServerChannel(remoteAddress);
        buf.readBytes(bytes);
        buf.release();
        sc.writeAndFlush(bytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ChannelPool.flushServer(remoteAddress);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        String ra = ctx.channel().remoteAddress().toString();
        ChannelPool.releaseChannel(ra);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ChannelPool.setClientChannel(remoteAddress, ctx.channel());
        Lock.Connection.release(remoteAddress);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.info(cause.getMessage());
        ctx.close();
    }

}