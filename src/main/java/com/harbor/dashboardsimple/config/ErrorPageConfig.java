package com.harbor.dashboardsimple.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 
 * @ClassName: ErrorPageConfig
 * @Description: 配置错误页面
 * @author harbor
 * @date 2018年9月12日 下午3:45:49
 */
@Configuration
public class ErrorPageConfig {

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                //ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html");
                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/errorPage/403.html");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errorPage/404");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errorPage/500");

                //container.addErrorPages(error400Page, error401Page, error404Page, error500Page);
                container.addErrorPages(error401Page, error404Page, error500Page);
            }
        };
    }

}
