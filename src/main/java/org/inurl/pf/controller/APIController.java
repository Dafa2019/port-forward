package org.inurl.pf.controller;

import org.inurl.pf.model.ApiResult;
import org.inurl.pf.model.Router;
import org.inurl.pf.service.PortForwardService;
import org.inurl.pf.support.Auth;
import org.inurl.pf.support.FlowAnalysisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author raylax
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class APIController {

    private final PortForwardService portForwardService;

    @Autowired
    public APIController(PortForwardService portForwardService) {
        this.portForwardService = portForwardService;
    }

    @Auth
    @PostMapping("/route")
    public ApiResult addRoute(@RequestBody Router router) {
        portForwardService.addRouteMapping(router);
        return ApiResult.ok();
    }

    @Auth
    @DeleteMapping("/route/{id}")
    public ApiResult deleteRoute(@PathVariable String id) {
        portForwardService.removeRouteMapping(id);
        return ApiResult.ok();
    }

    @GetMapping("/routes")
    public ApiResult routers() {
        return ApiResult.ok(portForwardService.getRoutes());
    }

    @GetMapping("/flow")
    public ApiResult getFlowTotal() {
        return ApiResult.ok(FlowAnalysisUtil.getTotal());
    }

    @GetMapping("/flow/{id}")
    public ApiResult getFlow(@PathVariable String id) {
        return ApiResult.ok(FlowAnalysisUtil.get(id));
    }

}
