package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.SysRole;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.service.SysRoleService;
import com.yzx.layuicms.vo.roleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/role")
public class roleController {

    @Autowired
    private SysRoleService service;

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
        this.service.page(page,wrapper);

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
            this.service.save(role);
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
            this.service.updateById(role);
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
            this.service.removeById(id);
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
    public dataGridView loadRoleTree() {
        List<SysPermission> list = this.permissionService.list();
        return new dataGridView(list);
    }

}
