package com.yzx.layuicms;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.common.constant;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.service.SysRoleService;
import com.yzx.layuicms.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LayuicmsApplicationTests {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPermissionService permissionService;

    @Test
    void contextLoads() {
        //创建筛选条件对象
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //设置筛选条件
        wrapper.eq("loginname","ww");
        //根据筛选条件查找
        SysUser user = userService.getOne(wrapper);
        //根据用户id找到对应的角色id
        Integer roleIdByUserId = this.userService.findRoleIdByUserId(user.getId());
        //根据角色id找到对应的权限id
        List<Integer> permissionIdByRoleId = this.roleService.getPermissionIdByRoleId(roleIdByUserId);
        //根据权限id找到权限对应的权限标识符数组
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.select("percode");
        permissionQueryWrapper.in("id",permissionIdByRoleId);
        permissionQueryWrapper.eq("type", constant.TYPE_PERMISSION);
        List<SysPermission> list = this.permissionService.list(permissionQueryWrapper);
        List<String> perCodes = new ArrayList<>();
        for (SysPermission sysPermission : list) {
            perCodes.add(sysPermission.getPercode());
        }
        System.out.println(perCodes.toString());
    }

}
