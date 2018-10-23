package org.inurl.pf.model;

/**
 * 流量统计
 */
public class FlowAnalysis {

    /**
     * 接收流量
     */
    private long receive = 0;

    /**
     * 发送流量
     */
    private long send = 0;

    /**
     * 当前连接数
     */
    private long connect = 0;

    /**
     * 总连接数
     */
    private long totalConnect = 0;


    public void addReceive(long amount) {
        receive += amount;
    }

    public void addSend(long amount) {
        send += amount;
    }

    public void addConnect(long amount) {
        if (amount > 0)
            totalConnect += amount;
        connect += amount;
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
