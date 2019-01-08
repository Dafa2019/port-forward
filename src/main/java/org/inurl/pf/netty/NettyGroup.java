package org.inurl.pf.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * netty线程组
 * @author raylax
 */
public class NettyGroup {

    private static EventLoopGroup parentGroup;
    private static EventLoopGroup childGroup;
    private static EventLoopGroup clientGroup;

    static {
        parentGroup = new NioEventLoopGroup(2);
        childGroup = new NioEventLoopGroup(2);
        clientGroup = new NioEventLoopGroup(2);
    }

    public static EventLoopGroup getChildGroup() {
        return childGroup;
    }

    static EventLoopGroup getClientGroup() {
        return clientGroup;
    }

    public static EventLoopGroup getParentGroup() {
        return parentGroup;
    }

    public static void shutdown() {
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
        clientGroup.shutdownGracefully();
    }

}
