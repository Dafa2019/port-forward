package org.inurl.pf.support;

import org.inurl.pf.netty.NettyGroup;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * 应用生命周期监听
 * @author raylax
 * @date 2019/1/8
 */
@Component
public class ApplicationLifeCycleListener implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextStoppedEvent || event instanceof ContextClosedEvent) {
            NettyGroup.shutdown();
        }
    }

}
