package com.yzx.layuicms.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页核心内容控制器
 */
@Controller
public class mainController {

    @RequestMapping("/main")
    public String main() {
        return "/main";
    }

}
