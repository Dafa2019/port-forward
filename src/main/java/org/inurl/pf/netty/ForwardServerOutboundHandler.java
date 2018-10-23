package org.inurl.pf.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.inurl.pf.model.Router;

public class ForwardServerOutboundHandler extends ChannelOutboundHandlerAdapter {

    private static Log logger = LogFactory.getLog(ForwardServerOutboundHandler.class);


    private Router router;

    ForwardServerOutboundHandler(Router router) {
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