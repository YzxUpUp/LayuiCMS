package com.yzx.layuicms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * img网页相关的控制器
 */
@Controller
public class imgController {

    @RequestMapping("/img")
    public String img() {
        return "/img/images";
    }

}
