package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysDept;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysDeptService;
import com.yzx.layuicms.service.SysUserService;
import com.yzx.layuicms.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    SysUserService userService;

    @Autowired
    SysDeptService deptService;

    /**
     * 获取所有部门层级信息，并返回JSON格式字符串
     *
     * @return
     */
    @RequestMapping("/getUserTree")
    public dataGridView getUserTree() {
        List<SysDept> list = this.deptService.list();
        return new dataGridView(list);
    }

    /**
     * 获取所有用户信息，并返回JSON格式字符串
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/loadAllUser")
    public dataGridView loadAllUser(userVo userVo) {
        //根据页面传输的分页数据进行查询结果分页
        IPage<SysUser> page = new Page<>(userVo.getPage(), userVo.getLimit());

        //设置筛选条件
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(userVo.getName()), "name", userVo.getName());
        wrapper.like(StringUtils.isNotEmpty(userVo.getAddress()), "address", userVo.getAddress());
        wrapper.like(StringUtils.isNotEmpty(userVo.getRemark()), "remark", userVo.getRemark());
        wrapper.eq(userVo.getId() != null, "deptid", userVo.getId());
        wrapper.orderByAsc("ordernum");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.userService.page(page, wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加用户
     * @param userVo
     * @return
     */
    @RequestMapping("/addUser")
    public resultObj addUser(SysUser sysUser,
                             String hiredateString) {
        try {
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sp.parse(hiredateString);
            sysUser.setHiredate(parse);
            this.userService.save(sysUser);
            return resultObj.ADD_SUCCESS;
        }catch (Exception e){
            return resultObj.ADD_ERROR;
        }
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @RequestMapping("/deleteUser")
    public resultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return resultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return resultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新用户信息
     * @param userVo
     * @return
     */
    @RequestMapping("/updateUser")
    public resultObj updateUser(SysUser sysUser,
                                String hiredateString) {
        try {
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date parse = sp.parse(hiredateString);
            sysUser.setHiredate(parse);
            this.userService.updateById(sysUser);
            return resultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return resultObj.UPDATE_ERROR;
        }
    }

}
