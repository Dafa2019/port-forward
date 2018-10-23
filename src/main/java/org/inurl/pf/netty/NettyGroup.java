package org.inurl.pf.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

class NettyGroup {


    static EventLoopGroup parentGroup = new NioEventLoopGroup(2);
    static EventLoopGroup childGroup = new NioEventLoopGroup(2);

    static EventLoopGroup clientGroup = new NioEventLoopGroup(2);

}
