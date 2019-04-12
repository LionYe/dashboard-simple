package com.harbor.dashboardsimple.config;//package com.pertops.pretopsofficeautomation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)   //86400 * 30 即30天
public class SessionConfig {
	
}