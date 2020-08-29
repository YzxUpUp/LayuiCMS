package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.constant;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.BusCustomer;
import com.yzx.layuicms.domain.treeNode;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.service.BusCustomerService;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.vo.customerVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class customerController {

    @Autowired
    private BusCustomerService customerService;

    /**
     * 加载所有的客户信息
     * @param customerVo
     * @return
     */
    @RequestMapping("/loadAllCustomer")
    public dataGridView customer(customerVo customerVo) { //根据页面传来的参数自动封装一个customerVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusCustomer> page = new Page<>(customerVo.getPage(),customerVo.getLimit());

        QueryWrapper<BusCustomer> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(customerVo.getCustomername()),"customername",customerVo.getCustomername());
        wrapper.like(StringUtils.isNotEmpty(customerVo.getTelephone()),"telephone",customerVo.getTelephone());
        wrapper.like(StringUtils.isNotEmpty(customerVo.getConnectionperson()),"connectionperson",customerVo.getConnectionperson());
        wrapper.like(StringUtils.isNotEmpty(customerVo.getAddress()),"address",customerVo.getAddress());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.customerService.page(page,wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加客户
     * @param customer
     * @return
     */
    @RequestMapping("/addCustomer")
    public resultObj addCustomer(BusCustomer customer){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("customer:create")){
                //存在
                this.customerService.save(customer);
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
     * 更新客户
     * @param customer
     * @return
     */
    @RequestMapping("/updateCustomer")
    public resultObj updateCustomer(BusCustomer customer){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("customer:update")){
                //存在
                this.customerService.updateById(customer);
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
     * 根据id删除单条客户信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteCustomer")
    public resultObj deleteCustomer(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("customer:delete")){
                //存在
                this.customerService.removeById(id);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return resultObj.DELETE_ERROR;
        }

    }

}
