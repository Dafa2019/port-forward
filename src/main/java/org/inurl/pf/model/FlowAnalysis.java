package org.inurl.pf.model;

/**
 * 流量统计
 * @author raylax
 */
public class FlowAnalysis {

    /**
     * 接收流量
     */
    private volatile long receive = 0;

    /**
     * 发送流量
     */
    private volatile long send = 0;

    /**
     * 当前连接数
     */
    private volatile long connect = 0;

    /**
     * 总连接数
     */
    private volatile long totalConnect = 0;


    public synchronized void addReceive(long amount) {
        receive += amount;
    }

    public synchronized void addSend(long amount) {
        send += amount;
    }

    public synchronized void addConnect(long amount) {
        connect += amount;
    }

    public synchronized void addTotalConnect(long amount) {
        totalConnect += amount;
    }

    public long getReceive() {
        return receive;
    }

    public long getSend() {
        return send;
    }

    public long getConnect() {
        return connect;
    }

    public long getTotalConnect() {
        return totalConnect;
    }
}
