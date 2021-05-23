package com.huitong.server.service.bo;

import com.huitong.server.model.RouteConfig;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 18:04
 */

@Slf4j
@Data
@Builder
@AllArgsConstructor
public class RouteClassified {

    private List<RouteConfig> routeConfigList;
    private List<RouteDefinition> activateRouteList ;
    private List<RouteDefinition> inValidRouteList ;

    public void classifyRoute() {
        if (CollectionUtils.isEmpty(routeConfigList)) {
            return;
        }
        if (activateRouteList == null) {
            activateRouteList = new ArrayList<>();
        }
        if(inValidRouteList == null) {
            inValidRouteList = new ArrayList<>();
        }
        for (RouteConfig config : routeConfigList) {
            if (config.getStatus() == 1) {
                // 启动的路由配置
                try {
                    activateRouteList.add(config.toRouteDefinition());
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            } else if (config.getStatus() == 0) {
                // 失效的路由配置
                try {
                    inValidRouteList.add(config.toRouteDefinition());
                } catch (Exception e) {
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
}
