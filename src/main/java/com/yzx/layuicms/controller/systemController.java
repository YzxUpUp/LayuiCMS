package com.yzx.layuicms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class systemController {

//    @RequestMapping("/img")
//    public String img() {
//        return "/img/images";
//    }

    /**
     * 跳转首页中心部分
     * @return
     */
    @RequestMapping("/main")
    public String main() {
        return "/main";
    }

//    @RequestMapping("/news")
//    public String news() {
//        return "/news/newsList";
//    }

    /**
     * 跳转日志信息页面
     * @return
     */
    @RequestMapping("/loginfo")
    public String loginfo() {
        return "/loginfo/loginfoManager";
    }

}
