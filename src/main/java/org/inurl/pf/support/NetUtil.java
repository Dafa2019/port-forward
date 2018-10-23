package org.inurl.pf.support;


import java.io.IOException;
import java.net.ServerSocket;

public class NetUtil {

    /**
     * 检查端口是否可用
     * @param port 端口
     * @return 是否可用
     */
    public static boolean protCheck(int port) {
        ServerSocket socket;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            return false;
        }
        try {
            socket.close();
        } catch (IOException ignored) {

        }
        return true;
    }

}
