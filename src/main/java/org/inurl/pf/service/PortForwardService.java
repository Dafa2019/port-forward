package org.inurl.pf.service;

import org.inurl.pf.model.Router;

import java.util.List;

/**
 * 端口转发
 * @author raylax
 */
public interface PortForwardService {

    /**
     * 添加转发路由
     * @param router 路由映射
     */
    void addRouteMapping(Router router);

    /**
     * 删除路由映射
     * @param id 路由ID
     */
    void removeRouteMapping(String id);

    /**
     * 获取所有路由
     * @return 所有路由
     */
    List<Router> getRoutes();

}
