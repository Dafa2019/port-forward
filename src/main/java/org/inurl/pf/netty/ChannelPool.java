package org.inurl.pf.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ChannelPool {

    private static Map<String, Channel> serverChannel = new ConcurrentHashMap<>();

    private static Map<String, Channel> clientChannel = new ConcurrentHashMap<>();

    static void flushClient(String ra) {
        flush(getClientChannel(ra));
    }

    static void flushServer(String ra) {
        flush(getServerChannel(ra));
    }

    private static void flush(Channel channel) {
        if (channel != null)
            channel.flush();
    }

    static void setServerChannel(String ra, Channel channel) {
        serverChannel.put(ra, channel);
    }

    static void setClientChannel(String ra, Channel channel) {
        clientChannel.put(ra, channel);
    }

    static Channel getServerChannel(String ra) {
        return serverChannel.get(ra);
    }

    static Channel getClientChannel(String ra) {
        return clientChannel.get(ra);
    }

    static void releaseChannel(String ra) {
        Channel c = getClientChannel(ra);
        if (c != null) {
            if (c.isOpen())
                c.close();
            clientChannel.remove(ra);
        }
        Channel s = getServerChannel(ra);
        if (s != null) {
            if (s.isOpen())
                s.close();
            serverChannel.remove(ra);
        }
    }

}
