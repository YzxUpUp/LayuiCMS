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
     * 跳转到菜单管理页面 - left
     * @return
     */
    @RequestMapping("/menuLeft")
    public String menuLeft() {
        return "/system/menu/menuLeft";
    }

    /**
     * 跳转到菜单管理页面 - right
     * @return
     */
    @RequestMapping("/menuRight")
    public String menuRight() {
        return "/system/menu/menuRight";
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("/permission")
    public String permission() {
        return "/system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理页面 - left
     * @return
     */
    @RequestMapping("/permissionLeft")
    public String permissionLeft() {
        return "/system/permission/permissionLeft";
    }

    /**
     * 跳转到权限管理页面 - right
     * @return
     */
    @RequestMapping("/permissionRight")
    public String permissionRight() {
        return "/system/permission/permissionRight";
    }

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/role")
    public String role() {
        return "/system/role/roleManager";
    }

    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping("/user")
    public String user() {
        return "/system/user/userManager";
    }

    /**
     * 跳转到用户管理页面 - left
     * @return
     */
    @RequestMapping("/userLeft")
    public String userLeft() {
        return "/system/user/userLeft";
    }

    /**
     * 跳转到用户管理页面 - right
     * @return
     */
    @RequestMapping("/userRight")
    public String userRight() {
        return "/system/user/userRight";
    }

}
