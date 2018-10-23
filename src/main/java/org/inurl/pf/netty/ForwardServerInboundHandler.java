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

public class ForwardServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardServerInboundHandler.class);

    private Router router;

    ForwardServerInboundHandler(Router router) {
        this.router = router;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg;
        int len = buf.readableBytes();
        FlowAnalysisUtil.addSendFlow(router.getNo(), len);
        byte[] bytes = new byte[len];
        buf.readBytes(bytes);
        String ra = ctx.channel().remoteAddress().toString();
        Lock.Connection.wait(ra);
        Channel cc = ChannelPool.getClientChannel(ctx.channel().remoteAddress().toString());
        cc.writeAndFlush(bytes);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ChannelPool.flushClient(ctx.channel().remoteAddress().toString());
    }

    /**
     * 断开触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("断开连接 : " + ctx.channel().remoteAddress());
        FlowAnalysisUtil.addConnectFlow(router.getNo(), -1);
        String ra = ctx.channel().remoteAddress().toString();
        ChannelPool.releaseChannel(ra);
        ctx.close();
    }

    /**
     * 传入触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("传入连接 : " + ctx.channel().remoteAddress());
        FlowAnalysisUtil.addConnectFlow(router.getNo(), 1);
        String ra = ctx.channel().remoteAddress().toString();
        Lock.Connection.init(ra);
        ChannelPool.setServerChannel(ra, ctx.channel());
        new ForwardClient(router, ra).init();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.debug("异常断开 : " + cause.getMessage());
        ctx.close();
    }

}