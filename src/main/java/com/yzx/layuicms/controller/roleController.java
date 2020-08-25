package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.constant;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.SysRole;
import com.yzx.layuicms.domain.treeNode;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.service.SysRoleService;
import com.yzx.layuicms.vo.roleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/role")
public class roleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 加载所有的角色信息
     * @param roleVo
     * @return
     */
    @RequestMapping("/loadAllRole")
    public dataGridView role(roleVo roleVo) { //根据页面传来的参数自动封装一个roleVo对象
        //将页面设置的分页信息传入page对象
        IPage<SysRole> page = new Page<>(roleVo.getPage(),roleVo.getLimit());

        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(roleVo.getName()),"name",roleVo.getName());
        wrapper.like(StringUtils.isNotEmpty(roleVo.getRemark()),"remark",roleVo.getRemark());
        wrapper.orderByDesc("createtime");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.roleService.page(page,wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加角色
     * @param role
     * @return
     */
    @RequestMapping("/addRole")
    public resultObj addRole(SysRole role){
        try {
            role.setCreatetime(new Date());
            this.roleService.save(role);
            return resultObj.ADD_SUCCESS;
        }catch (Exception e){
            return resultObj.ADD_ERROR;
        }
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @RequestMapping("/updateRole")
    public resultObj updateRole(SysRole role){
        try {
            this.roleService.updateById(role);
            return resultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return resultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据id删除单条角色信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteRole")
    public resultObj deleteRole(Integer id) {

        System.out.println(id);

        try {
            this.roleService.removeById(id);
            return resultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return resultObj.DELETE_ERROR;
        }

    }

    /**
     * 加载权限树
     * @return
     */
    @RequestMapping("/loadRoleTree")
    public dataGridView loadRoleTree(Integer roleId) {
        //1、获取所有的菜单和权限
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.eq("available", constant.AVAILABLE_TRUE);
        List<SysPermission> allPermissions = this.permissionService.list(wrapper);

        //2、获取角色id对应的菜单和权限id
        List<Integer> rolePermissionsId = this.roleService.getPermissionIdByRoleId(roleId);

        //3、获取角色id对应的菜单和权限信息
        if(rolePermissionsId.size() > 0){
            //4、根据权限信息生成权限树
            List<treeNode> nodes = new ArrayList<>();
            for(SysPermission p1 : allPermissions){
                String checkArr = "0";
                for (Integer rpId : rolePermissionsId) {
                    if(p1.getId() == rpId){
                        checkArr = "1";
                        break;
                    }
                }
                Boolean spread = p1.getOpen() == 1 ? true : false;
                nodes.add(new treeNode(p1.getId(),p1.getPid(),p1.getTitle(),spread,checkArr));
            }
            return new dataGridView(nodes);
        }else {
            return null;
        }

    }

    /**
     * 分配角色权限的方法
     * @param roleId
     * @param ids
     * @return
     */
    @RequestMapping("/saveRolePermission")
    public resultObj saveRolePermission(Integer roleId,
                                        Integer[] ids){
        try {
            //首先删除对应roleId的所有权限信息
            this.roleService.deleteRolePermission(roleId);
            //再重新添加新分配的权限信息
            if(ids != null && ids.length > 0){
                for (Integer pid : ids) {
                    this.roleService.saveRolePermission(roleId,pid);
                }
                return resultObj.DISPATCH_SUCCESS;
            }else{
                return resultObj.DISPATCH_ERROR;
            }
        }catch (Exception e){
            return resultObj.DISPATCH_ERROR;
        }

    }

}
