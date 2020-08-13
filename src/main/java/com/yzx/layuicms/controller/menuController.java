package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.layuicms.common.*;
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

/**
 * 首页左侧导航树控制类
 */
@RestController //返回JSON数据串
@RequestMapping("/menu")
public class menuController {

    @Autowired
    private SysPermissionService permissionService;

    @RequestMapping("/loadIndexLeftMenuJson")
    public dataGridView loadIndexLeftMenuJson(permissionVo permissionVo) {
        //查询所有菜单
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", constant.TYPE_MENU);
        queryWrapper.eq("available", constant.AVAILABLE_TRUE);
        //获取当前登录的用户信息
        activerUser loginUser = (activerUser) SecurityUtils.getSubject().getPrincipal();
        SysUser user = loginUser.getUser();
        //创建一个用于装载权限信息的集合
        List<SysPermission> list = null;
        if (user.getType() == constant.TYPE_ADMIN) {
            //如果用户是超级管理员，直接获取所有资源树
            list = permissionService.list(queryWrapper);
        } else {
            //否则根据用户ID+角色+权限去查询
            list = permissionService.list(queryWrapper);
        }

        //创建新的树节点集合
        List<treeNode> treeNodes = new ArrayList<>();
        //遍历当前用户的所有权限信息，放入节点集合中
        for (SysPermission p : list) {
            Integer id = p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen() == constant.OPEN_TRUE ? true : false;
            treeNodes.add(new treeNode(id, pid, title, icon, href, spread));
        }
        //根据节点集合，构造层级关系
        List<treeNode> list2 = treeNodeBuilder.build(treeNodes, 1);

        //根据完整的层级关系创建完整的左侧导航树，转换为JSON返回
        dataGridView dataGridView = new dataGridView(list2);
        System.out.println(dataGridView.toString());
        return dataGridView;
    }


}
