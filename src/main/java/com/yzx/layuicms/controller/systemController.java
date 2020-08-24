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
        return "/system/loginfo/loginfoManager";
    }

    /**
     * 跳转到公告页面
     * @return
     */
    @RequestMapping("/notice")
    public String notice() {
        return "/system/notice/noticeManager";
    }

    /**
     * 跳转到图标页面
     * @return
     */
    @RequestMapping("/icon")
    public String icon() {
        return "/system/icon";
    }

    /**
     * 跳转到部门管理页面
     * @return
     */
    @RequestMapping("/dept")
    public String dept() {
        return "/system/dept/deptManager";
    }

    /**
     * 跳转到部门管理页面 - left
     * @return
     */
    @RequestMapping("/deptLeft")
    public String deptLeft() {
        return "/system/dept/deptLeft";
    }

    /**
     * 跳转到部门管理页面 - right
     * @return
     */
    @RequestMapping("/deptRight")
    public String deptRight() {
        return "/system/dept/deptRight";
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("/menu")
    public String menu() {
        return "/system/menu/menuManager";
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("/menuLeft")
    public String menuLeft() {
        return "/system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("/menuRight")
    public String menuRight() {
        return "/system/menu/menuRight";
    }

}
