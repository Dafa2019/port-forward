package org.inurl.pf.model;


/**
 * 转发路由
 */
public class Router {

    /**
     * 本地端口
     */
    private int port;

    /**
     * 本地地址
     */
    private String host;

    /**
     * 目标端口
     */
    private int targetPort;

    /**
     * 目标地址
     */
    private String targetHost;

    public Router(int port, String targetHost) {
        this.port = port;
        this.host = "0.0.0.0";
        this.targetHost = targetHost;
    }

    public Router(int port, String host, String targetHost) {
        this.port = port;
        this.targetPort = port;
        this.host = host;
        this.targetHost = targetHost;
    }

    public Router(int port, String host, int targetPort, String targetHost) {
        this.port = port;
        this.targetPort = targetPort;
        this.host = host;
        this.targetHost = targetHost;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTargetHost() {
        return targetHost;
    }

    public void setTargetHost(String targetHost) {
        this.targetHost = targetHost;
    }


    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Router))
            return false;
        return toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return host + ":" + port + " -> " + targetHost + ":" + targetPort;
    }
}
