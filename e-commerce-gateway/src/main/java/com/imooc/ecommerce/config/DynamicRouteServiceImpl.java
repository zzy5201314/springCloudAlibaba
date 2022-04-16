package com.imooc.ecommerce.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 事件推送 Aware: 动态更新路由网关 Service
 *
 * @author zzy
 * @date 2022/4/16
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    /**
     * 写路由定义  -- gateway 中
     */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 获取路由定义
     */
    private final RouteDefinitionLocator routeDefinitionLocator;

    /**
     * 事件发布对象
     */
    private ApplicationEventPublisher publisher;

    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter,
                                   RouteDefinitionLocator routeDefinitionLocator) {

        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

        // 完成事件推送句柄的初始化
        this.publisher = applicationEventPublisher;
    }

    /**
     * 增加路由定义
     *
     * @param definition
     * @return
     */
    public String addRouteDefinition(RouteDefinition definition) {

        log.info("gateway add route: [{}]", definition);

        // 保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        // 发布事件通知给 Gateway，同步新增的路由定义
        this.publisher.publishEvent(new RefreshRoutesEvent(this));

        return "success";
    }

    /**
     * 更新路由
     *
     * @param definitions
     * @return
     */
    public String updateList(List<RouteDefinition> definitions) {

        log.info("gateway update route: [{}]", JSON.toJSONString(definitions));

        // 先拿到当前 Gateway 中存储的路由定义
        List<RouteDefinition> routeDefinitionsExits =
                routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitionsExits)) {
            // 清楚掉之前所有的 “旧的” 路由定义
            routeDefinitionsExits.forEach(rd -> {
                log.info("delete route definition: [{}]", rd);
                deleteById(rd.getId());
            });
        }

        // 把更新的路由定义更新到 gateway 中
        definitions.forEach(definition -> {
            updateByRouteDefinition(definition);
        });
        return "success";
    }

    /**
     * 根据路由 id 删除路由配置（id 不会重复）
     *
     * @param id
     * @return
     */
    private String deleteById(String id) {

        try {
            log.info("gateway delete route id: [{}]", id);
            // 删除路由配置并发布
            routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            // 发布事件通知给 Gateway，更新路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(id));
            return "delete success";
        } catch (Exception ex) {
            log.error("gateway delete route fail: [{}]", ex.getMessage(), ex);
            return "delete fail";
        }
    }

    /**
     * 跟新路由
     * 更新的实现策略：删除 + 新增 = 更新
     *
     * @param definition
     * @return
     */
    private String updateByRouteDefinition(RouteDefinition definition) {

        try {
            log.info("gateway update route: [{}]", definition);
            // 删除路由配置
            routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception ex) {
            return "update fail，not find route routeId:" + definition.getId();
        }

        try {
            // 保存路由配置并发布
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            // 发布事件通知给 Gateway，同步新增的路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(new RefreshRoutesEvent(this)));
            return "success";
        } catch (Exception ex) {
            return "update route fail";
        }
    }
}
