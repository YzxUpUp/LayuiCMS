package com.yzx.layuicms.controller;

import com.yzx.layuicms.common.resultObj;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("info:view")){
            //存在
            return "/system/loginfo/loginfoManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到公告页面
     * @return
     */
    @RequestMapping("/notice")
    public String notice() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("notice:view")){
            //存在
            return "/system/notice/noticeManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
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
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("dept:view")){
            //存在
            return "/system/dept/deptManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到部门管理页面 - left
     * @return
     */
    @RequestMapping("/deptLeft")
    public String deptLeft() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("dept:view")){
            //存在
            return "/system/dept/deptLeft";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到部门管理页面 - right
     * @return
     */
    @RequestMapping("/deptRight")
    public String deptRight() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("dept:view")){
            //存在
            return "/system/dept/deptRight";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到菜单管理页面
     * @return
     */
    @RequestMapping("/menu")
    public String menu() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("menu:view")){
            //存在
            return "/system/menu/menuManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到菜单管理页面 - left
     * @return
     */
    @RequestMapping("/menuLeft")
    public String menuLeft() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("menu:view")){
            //存在
            return "/system/menu/menuLeft";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到菜单管理页面 - right
     * @return
     */
    @RequestMapping("/menuRight")
    public String menuRight() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("menu:view")){
            //存在
            return "/system/menu/menuRight";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("/permission")
    public String permission() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("permission:view")){
            //存在
            return "/system/permission/permissionManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到权限管理页面 - left
     * @return
     */
    @RequestMapping("/permissionLeft")
    public String permissionLeft() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("permission:view")){
            //存在
            return "/system/permission/permissionLeft";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到权限管理页面 - right
     * @return
     */
    @RequestMapping("/permissionRight")
    public String permissionRight() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("permission:view")){
            //存在
            return "/system/permission/permissionRight";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/role")
    public String role() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("role:view")){
            //存在
            return "/system/role/roleManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到用户管理页面
     * @return
     */
    @RequestMapping("/user")
    public String user() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("user:view")){
            //存在
            return "/system/user/userManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到用户管理页面 - left
     * @return
     */
    @RequestMapping("/userLeft")
    public String userLeft() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("user:view")){
            //存在
            return "/system/user/userLeft";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到用户管理页面 - right
     * @return
     */
    @RequestMapping("/userRight")
    public String userRight() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("user:view")){
            //存在
            return "/system/user/userRight";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到修改密码界面
     * @return
     */
    @RequestMapping("/restPassword")
    public String restPassword() {
        return "/system/user/restPassword";
    }

    /**
     * 跳转到客户管理界面
     * @return
     */
    @RequestMapping("/customer")
    public String customer() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("customer:view")){
            //存在
            return "/system/customer/customerManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到供应商管理界面
     * @return
     */
    @RequestMapping("/provider")
    public String provider() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("provider:view")){
            //存在
            return "/system/provider/providerManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到商品管理界面
     * @return
     */
    @RequestMapping("/goods")
    public String goods() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("goods:view")){
            //存在
            return "/system/goods/goodsManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到进货单管理界面
     * @return
     */
    @RequestMapping("/goodsInport")
    public String goodsInport() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("inport:view")){
            //存在
            return "/system/goodsInport/goodsInportManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到进货退货单管理界面
     * @return
     */
    @RequestMapping("/goodsOutport")
    public String goodsOutport() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("outport:view")){
            //存在
            return "/system/goodsOutport/goodsOutportManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到销货单管理界面
     * @return
     */
    @RequestMapping("/goodsSales")
    public String goodsSales() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("sales:view")){
            //存在
            return "/system/goodsSales/goodsSalesManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

    /**
     * 跳转到销货退货单管理界面
     * @return
     */
    @RequestMapping("/goodsSalesback")
    public String goodsSalesback() {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("salesback:view")){
            //存在
            return "/system/goodsSalesback/goodsSalesbackManager";
        }else{
            //不存在
            return "/error/noAuth";
        }
    }

}
