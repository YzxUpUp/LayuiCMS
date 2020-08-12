package com.yzx.layuicms.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Configuration
@RequestMapping("/index")
public class indexController {

    @GetMapping
    public String getIndex() {
        return "/index";
    }

}
