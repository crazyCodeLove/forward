package com.huitong.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 16:48
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteConfig {
    private String id;
    private String systemName;
    private String systemSign;
    private String pattern;
    private String desUri;
    private Integer status;//状态。 1:启用，0：禁用
    private Date updateTime;

    public RouteDefinition toRouteDefinition() throws URISyntaxException {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(id);
        List<PredicateDefinition> predicates = new ArrayList<>();
        PredicateDefinition predicate = new PredicateDefinition();
        //注意name
        predicate.setName("Path");
        predicate.addArg("pattern", pattern);
        predicates.add(predicate);
        routeDefinition.setPredicates(predicates);

        URI uri = new URI(desUri);
        routeDefinition.setUri(uri);
        routeDefinition.setOrder(0);
        return routeDefinition;
    }
}
