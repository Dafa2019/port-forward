package org.inurl.pf.service;

import org.inurl.pf.model.Router;

import java.util.List;

/**
 * 端口转发
 */
public interface PortForwardService {


    void addRouteMapping(Router router);

    void removeRouteMapping(String id);

    List<Router> getRoutes();

}
