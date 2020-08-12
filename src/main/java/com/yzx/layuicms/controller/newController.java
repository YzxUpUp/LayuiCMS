package com.yzx.layuicms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文章通知网页控制器
 */
@Controller
public class newController {

    @RequestMapping("/news")
    public String news() {
        return "/news/newsList";
    }

}
