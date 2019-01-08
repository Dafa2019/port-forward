package org.inurl.pf.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author raylax
 */
public class ForwardClientInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardClientInboundHandler.class);

    private final Channel serverChannel;

    ForwardClientInboundHandler(Channel serverChannel) {
        this.serverChannel = serverChannel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (serverChannel != null) {
            serverChannel.write(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        if (serverChannel != null) {
            serverChannel.flush();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (serverChannel != null) {
            serverChannel.close();
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // noop
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("上游异常断开", cause);
        ctx.close();
    }

}