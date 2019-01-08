package org.inurl.pf.netty;

/**
 * @author raylax
 * @date 2019/1/8
 */
public interface Client {

    /**
     * 连接
     */
    void connect();

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 写出数据
     * @param msg message
     */
    void write(Object msg);

    /**
     * 刷新缓冲区
     */
    void flush();

}
