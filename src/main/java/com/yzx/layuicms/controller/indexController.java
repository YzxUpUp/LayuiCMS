package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.domain.SysLoginfo;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysLoginfoService;
import com.yzx.layuicms.service.SysUserService;
import com.yzx.layuicms.util.ipUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 首页控制器
 */
@Configuration
@RequestMapping("/index")
public class indexController {

    @Autowired
    SysLoginfoService loginfoService;

    @Autowired
    SysUserService userService;

    /**
     * 跳转到首页
     * @param session
     * @return
     */
    @GetMapping
    public String getIndex(HttpSession session,
                           HttpServletRequest request) {
        activerUser loginUser = (activerUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = loginUser.getUser();

        //设置筛选条件
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //根据登录用户名获取一个user对象
        wrapper.eq("loginname",user.getLoginname());
        SysUser User = this.userService.getOne(wrapper);

        //创建新的日志对象
        SysLoginfo loginfo = new SysLoginfo();
        //设置登陆用户名用户名
        loginfo.setLoginname(User.getName());
        //设置登录ip
        loginfo.setLoginip(ipUtil.getIpAddr(request));
        //设置登录时间
        loginfo.setLogintime(new Date());
        //在数据库中添加记录
        this.loginfoService.save(loginfo);

        session.setAttribute("user",user);
        return "index";
    }

}
