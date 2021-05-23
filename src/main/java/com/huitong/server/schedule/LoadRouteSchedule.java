package com.huitong.server.schedule;

import com.huitong.server.service.LoadRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 19:55
 */

@Component
public class LoadRouteSchedule {

    @Autowired
    private LoadRouteService routeService;

    @Scheduled(cron = "0 0/2 * * * ?")
    public void autoRefreshRoute() {
        routeService.refreshRoute();
    }
}
