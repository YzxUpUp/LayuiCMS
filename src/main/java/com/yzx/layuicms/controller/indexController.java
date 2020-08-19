package com.yzx.layuicms.controller;

import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 首页控制器
 */
@Configuration
@RequestMapping("/index")
public class indexController {

    /**
     * 跳转到首页
     * @param session
     * @return
     */
    @GetMapping
    public String getIndex(HttpSession session) {
        activerUser loginUser = (activerUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = loginUser.getUser();
        session.setAttribute("user",user);
        return "index";
    }

}
