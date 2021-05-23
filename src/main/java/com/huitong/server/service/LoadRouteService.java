package com.huitong.server.service;

import com.huitong.server.dao.mapper.RouteConfigMapper;
import com.huitong.server.model.RouteConfig;
import com.huitong.server.service.bo.RouteClassified;
import com.huitong.server.util.CalendarUtil;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 17:18
 */

@Service
public class LoadRouteService {

    private RouteConfigMapper routeMapper;
    private DynamicRouteService dynamicRouteService;

    private Set<String> patternTimeSet = new HashSet<>();

    public LoadRouteService(RouteConfigMapper routeMapper, DynamicRouteService dynamicRouteService) {
        this.routeMapper = routeMapper;
        this.dynamicRouteService = dynamicRouteService;
    }

    public void refreshRoute() {
        List<RouteConfig> routeConfigList = routeMapper.selectAll();
        if (routeConfigList.isEmpty()) {
            return;
        }
        if (!routeChanged(routeConfigList)) {
            return;
        }

        RouteClassified routeClassified = RouteClassified.builder().routeConfigList(routeConfigList).build();
        routeClassified.classifyRoute();
        dynamicRouteService.deleteRouteBatch(routeClassified.getInValidRouteList());
        dynamicRouteService.addRouteBatch(routeClassified.getActivateRouteList());
        patternTimeSet.clear();
        for (RouteConfig config:routeConfigList) {
            patternTimeSet.add(buildKey(config));
        }
    }

    private boolean routeChanged(List<RouteConfig> routeConfigList) {
        for (RouteConfig config:routeConfigList) {
            if (!patternTimeSet.contains(buildKey(config))) {
                return true;
            }
        }
        return false;
    }

    private String buildKey(RouteConfig config) {
        return config.getPattern() + CalendarUtil.formatDate(config.getUpdateTime(), CalendarUtil.DATE_TIME_PATTERN_SIMPLE);
    }
}
