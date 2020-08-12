package com.yzx.layuicms.controller;

import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.domain.SysUser;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 */
@Configuration
@RequestMapping("/index")
public class indexController {

    @GetMapping
    public String getIndex(Model model) {
        activerUser loginUser = (activerUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = loginUser.getUser();
        model.addAttribute("user",user);
        return "index";
    }

}
