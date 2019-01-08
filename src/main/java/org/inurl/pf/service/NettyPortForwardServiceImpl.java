package org.inurl.pf.service;

import org.inurl.pf.model.Router;
import org.inurl.pf.netty.ForwardServer;
import org.inurl.pf.netty.Server;
import org.inurl.pf.support.FlowAnalysisUtil;
import org.inurl.pf.support.NetUtil;
import org.inurl.pf.support.ServiceException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author raylax
 */
@Service
public class NettyPortForwardServiceImpl implements PortForwardService {

    private final List<Router> routers = new CopyOnWriteArrayList<>();
    private final List<Server> servers = new CopyOnWriteArrayList<>();
    private final Map<String, Integer> idxMap = new ConcurrentHashMap<>();

    @Override
    public synchronized void addRouteMapping(Router router) {
        if (!NetUtil.portCheck(router.getPort())) {
            throw new ServiceException("该端口不可用");
        }
        final int idx = routers.size();
        ForwardServer server = new ForwardServer(router);
        idxMap.put(router.getNo(), idx);
        server.start();
    }

    @Override
    public synchronized void removeRouteMapping(String id) {
        int idx = idxMap.get(id);
        routers.remove(idx);
        Server server = servers.remove(idx);
        server.stop();
    }

    @Override
    public List<Router> getRoutes() {
        List<Router> routerList = routers;
        routerList.forEach(item -> item.setFlow(FlowAnalysisUtil.get(item.getNo())));
        return routerList;
    }

}
