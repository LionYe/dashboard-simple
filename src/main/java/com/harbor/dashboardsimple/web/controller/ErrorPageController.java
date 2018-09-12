package com.harbor.dashboardsimple.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: ErrorPageAction
 * @Description: 错误页面控制器
 * @author harbor
 * @date 2018年9月12日 下午2018/9/12
 */
@Controller
@RequestMapping(value = "/errorPage")
public class ErrorPageController {

    /**
     * 
     * @Title: notFound
     * @Description: 配置404
     * @return
     */
    @RequestMapping(value = "/404")
    public String notFound() {
        return "404";
    }

    @RequestMapping(value = "/500")
    public String serverError() {
        return "500";
    }
    
}