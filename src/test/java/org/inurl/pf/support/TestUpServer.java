package org.inurl.pf.support;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.inurl.pf.model.Router;
import org.inurl.pf.netty.ForwardServerInboundHandler;
import org.inurl.pf.netty.NettyGroup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 用于模拟上游服务器
 * @author raylax
 * @date 2019/1/8
 */
public class TestUpServer {

    private ChannelInboundTest channelInboundTest = new ChannelInboundTest();
    private Channel channel;

    public TestUpServer(Router router) {

        EventLoopGroup parentGroup = NettyGroup.getParentGroup();
        EventLoopGroup childGroup = NettyGroup.getChildGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(parentGroup, childGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(channelInboundTest);
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true);
        channel = b.bind(router.getTargetPort()).syncUninterruptibly().channel();
    }

    public void stop() {
        channel.close().syncUninterruptibly();
    }

    public void read(byte[] dest) {
        channelInboundTest.getResult().readBytes(dest);
    }

    public boolean isConnected() {
        return channelInboundTest.isConnected();
    }

    public boolean isClosed() {
        return !isConnected();
    }


    @ChannelHandler.Sharable
    static class ChannelInboundTest extends ChannelInboundHandlerAdapter {

        private boolean connected;
        private final ByteBuf result = Unpooled.buffer();


        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            result.writeBytes((ByteBuf) msg);
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) {
            connected = false;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            connected = true;
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            connected = false;
        }

        boolean isConnected() {
            return connected;
        }

        ByteBuf getResult() {
            return result;
        }
    }

}

