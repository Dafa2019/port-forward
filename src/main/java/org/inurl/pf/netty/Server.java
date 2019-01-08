package org.inurl.pf.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author raylax
 * @date 2019/1/4
 */
public interface Server {

    /**
     * 启动服务器
     */
    void start();

    /**
     * 停止服务器
     */
    void stop();

}
