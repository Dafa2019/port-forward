package org.inurl.pf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

public class ForwardClientOutboundHandler extends ChannelOutboundHandlerAdapter {


    private static Log logger = LogFactory.getLog(ForwardServerOutboundHandler.class);

    private Router router;

    ForwardClientOutboundHandler(Router router) {
        this.router = router;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        byte[] bytes = (byte[])msg;
        ByteBuf buf = ctx.alloc().buffer(bytes.length);
        buf.writeBytes(bytes);
        ctx.writeAndFlush(buf);
    }

}
