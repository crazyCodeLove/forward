package com.huitong.server;

import com.huitong.server.service.LoadRouteService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 7:32
 */

@EnableScheduling
@MapperScan(basePackages = "com.huitong.server.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class StartServer implements ApplicationRunner {

    @Autowired
    private LoadRouteService routeService;

    public static void main(String[] args) {
        SpringApplication.run(StartServer.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        routeService.refreshRoute();
    }
}
