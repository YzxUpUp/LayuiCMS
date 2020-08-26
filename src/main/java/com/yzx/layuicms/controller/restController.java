package com.yzx.layuicms.controller;

import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysUserService;
import com.yzx.layuicms.util.saltUtil;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 修改密码和信息的控制类
 */
@Controller
@RequestMapping("/rest")
public class restController {

    @Autowired
    private SysUserService userService;

    @RequestMapping("/updatePwd")
    public String updatePwd(Integer id,
                            String password,
                            HttpSession session){
        try{
            //根据id获取当前登录用户
            SysUser user = this.userService.getById(id);
            //生成随机盐
            String salt = saltUtil.getSalt(8);
            //对密码进行加密
            Md5Hash md5Hash = new Md5Hash(password,salt,1024);
            //获取加密后的密码
            String relPassword = md5Hash.toHex();
            //为注册用户添加数据
            user.setSalt(salt);
            user.setPwd(relPassword);
            //插入数据
            this.userService.updateById(user);
            session.setAttribute("success","密码修改成功");
            return "redirect:/sys/restPassword";
        }catch (Exception e){
            session.setAttribute("error","密码修改失败");
            return "redirect:/sys/restPassword";
        }
    }

}
