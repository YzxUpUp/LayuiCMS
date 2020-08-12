package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.permissionVo;
import com.yzx.layuicms.common.treeNodeBuilder;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.domain.treeNode;
import com.yzx.layuicms.service.SysPermissionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class menuController {

    @Autowired
    private SysPermissionService permissionService;

    @RequestMapping("/loadIndexLeftMenuJson")
    public dataGridView loadIndexLeftMenuJson(permissionVo permissionVo) {
        //查询所有菜单
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", "menu");
        queryWrapper.eq("available", 1);

        activerUser loginUser = (activerUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = loginUser.getUser();
        List<SysPermission> list = null;
        if (user.getType() == 0) {
            list = permissionService.list(queryWrapper);
        } else {
            //根据用户ID+角色+权限去查询
            list = permissionService.list(queryWrapper);
        }


//        System.out.println(list.toString());

        List<treeNode> treeNodes = new ArrayList<>();
        for (SysPermission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen() == 1 ? true : false;
            treeNodes.add(new treeNode(id, pid, title, icon, href, spread));
        }
        //构造层级关系
        List<treeNode> list2 = treeNodeBuilder.build(treeNodes, 1);

        return new dataGridView(list2);
    }


}
