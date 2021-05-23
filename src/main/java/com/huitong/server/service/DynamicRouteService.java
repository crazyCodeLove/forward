package com.huitong.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 15:17
 */

@Component
public class DynamicRouteService implements ApplicationEventPublisherAware {
    @Autowired
    private InMemoryRouteDefinitionRepository routeDefinitionRepository;

    private ApplicationEventPublisher publisher;

    /**
     * 增加路由
     *
     * @param definition
     * @return
     */
    public void addRoute(RouteDefinition definition) {
        routeDefinitionRepository.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void addRouteBatch(List<RouteDefinition> routeDefinitionList) {
        if (CollectionUtils.isEmpty(routeDefinitionList)) {
            return;
        }
        for (RouteDefinition routeDefinition : routeDefinitionList) {
            routeDefinitionRepository.save(Mono.just(routeDefinition)).subscribe();
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void deleteRoute(RouteDefinition definition) {
        Flux<RouteDefinition> routeDefinitions = routeDefinitionRepository.getRouteDefinitions();
        Set<String> existRouteIds = new HashSet<>();
        routeDefinitions.toStream().forEach((r) -> {
            existRouteIds.add(r.getId());
        });
        if (!existRouteIds.contains(definition.getId())) {
            return;
        }
        routeDefinitionRepository.delete(Mono.just(definition.getId())).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    public void deleteRouteBatch(List<RouteDefinition> routeDefinitionList) {
        Flux<RouteDefinition> routeDefinitions = routeDefinitionRepository.getRouteDefinitions();
        Set<String> existRouteIds = new HashSet<>();
        routeDefinitions.toStream().forEach((r) -> {
            existRouteIds.add(r.getId());
        });
        for (RouteDefinition definition : routeDefinitionList) {
            if (!existRouteIds.contains(definition.getId())) {
                continue;
            }
            routeDefinitionRepository.delete(Mono.just(definition.getId())).subscribe();
        }
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

}
