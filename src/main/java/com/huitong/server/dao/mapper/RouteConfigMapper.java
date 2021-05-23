package com.huitong.server.dao.mapper;

import com.huitong.server.model.RouteConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * </p>
 * author pczhao <br/>
 * date  2021/5/23 16:56
 */

@Repository
public interface RouteConfigMapper {

    List<RouteConfig> selectAll();

}
