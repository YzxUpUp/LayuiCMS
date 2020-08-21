package com.yzx.layuicms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sys")
public class systemController {

    /**
     * 跳转首页中心部分
     * @return
     */
    @RequestMapping("/main")
    public String main() {
        return "/main";
    }

    /**
     * 跳转日志信息页面
     * @return
     */
    @RequestMapping("/loginfo")
    public String loginfo() {
        return "/loginfo/loginfoManager";
    }

    /**
     * 跳转到公告页面
     * @return
     */
    @RequestMapping("/notice")
    public String notice() {
        return "/notice/noticeManager";
    }

}
