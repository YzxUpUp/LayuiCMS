package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysDept;
import com.yzx.layuicms.service.SysDeptService;
import com.yzx.layuicms.vo.deptVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.beans.IntrospectionException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("/dept")
public class deptController {

    @Autowired
    SysDeptService service;

    /**
     * 获取所有部门层级信息，并返回JSON格式字符串
     *
     * @return
     */
    @RequestMapping("/getDeptTree")
    public dataGridView getDeptTree() {
        List<SysDept> list = this.service.list();
        return new dataGridView(list);
    }

    /**
     * 获取所有部门信息，并返回JSON格式字符串
     *
     * @param deptVo
     * @return
     */
    @RequestMapping("/loadAllDept")
    public dataGridView loadAllDept(deptVo deptVo) {
        //根据页面传输的分页数据进行查询结果分页
        IPage<SysDept> page = new Page<>(deptVo.getPage(), deptVo.getLimit());

        //设置筛选条件
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(deptVo.getTitle()), "title", deptVo.getTitle());
        wrapper.like(StringUtils.isNotEmpty(deptVo.getAddress()), "address", deptVo.getAddress());
        wrapper.like(StringUtils.isNotEmpty(deptVo.getRemark()), "remark", deptVo.getRemark());
        wrapper.eq(deptVo.getId() != null, "id", deptVo.getId())
                .or()
                .eq(deptVo.getId() != null, "pid", deptVo.getId());
        wrapper.orderByAsc("ordernum");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.service.page(page, wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加部门
     * @param sysDept
     * @return
     */
    @RequestMapping("/addDept")
    public resultObj addDept(SysDept sysDept) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("dept:create")){
                //存在
                sysDept.setCreatetime(new Date());
                this.service.save(sysDept);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.ADD_SUCCESS;
        }catch (Exception e){
            return resultObj.ADD_ERROR;
        }
    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("/deleteDept")
    public resultObj deleteDept(Integer id) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("dept:delete")){
                //存在
                this.service.removeById(id);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return resultObj.DELETE_ERROR;
        }
    }

    /**
     * 更新部门信息
     * @param sysDept
     * @return
     */
    @RequestMapping("/updateDept")
    public resultObj updateDept(SysDept sysDept) {
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("dept:update")){
                //存在
                this.service.updateById(sysDept);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return resultObj.UPDATE_ERROR;
        }
    }

    /**
     * 检查该部门是否有子部门
     * @param id
     * @return
     */
    @RequestMapping("/checkDept")
    public resultObj checkParent(Integer id) {
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //对主体对象的权限认证，是否有权限进行操作
        if(subject.isPermitted("dept:delete")){
            //存在
            QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",id);
            List<SysDept> list = this.service.list(wrapper);

            return list.size() > 0 ? resultObj.DELETE_ERROR : resultObj.DELETE_SUCCESS;
        }else{
            //不存在
            return resultObj.AUTH_ERROR;
        }
    }

}
