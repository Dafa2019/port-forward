package org.inurl.pf.service;

import org.inurl.pf.model.Router;
import org.inurl.pf.netty.ForwardServer;
import org.inurl.pf.support.FlowAnalysisUtil;
import org.inurl.pf.support.NetUtil;
import org.inurl.pf.support.RouteException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NettyPortForwardService implements PortForwardService {

    private final List<Router> routers = new ArrayList<>();
    private final List<ForwardServer> servers = new ArrayList<>();
    private final Map<String, Integer> idxMap = new HashMap<>();

    @Override
    public synchronized void addRouteMapping(Router router) {
        if (!NetUtil.portCheck(router.getPort())) {
            throw new RouteException("该端口不可用");
        }
        final int idx = routers.size();
        ForwardServer server = new ForwardServer(router);
        routers.add(router);
        servers.add(server);
        idxMap.put(router.getNo(), idx);
        server.start();
    }

    @Override
    public synchronized void removeRouteMapping(String id) {
        int idx = idxMap.get(id);
        routers.remove(idx);
        ForwardServer server = servers.remove(idx);
        server.interrupt();
    }

    @Override
    public List<Router> getRoutes() {
        List<Router> routerList = routers;
        routerList.forEach(item -> item.setFlow(FlowAnalysisUtil.get(item.getNo())));
        return routerList;
    }

}
