package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysLoginfo;
import com.yzx.layuicms.service.SysLoginfoService;
import com.yzx.layuicms.vo.logInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/loginfo")
public class logInfoController {

    @Autowired
    private SysLoginfoService service;

    /**
     * 加载所有的日志信息
     * @param logInfoVo
     * @return
     */
    @RequestMapping("/loadAllLoginfo")
    public dataGridView loginfo(logInfoVo logInfoVo) { //根据页面传来的参数自动封装一个logInfoVo对象
        //将页面设置的分页信息传入page对象
        IPage<SysLoginfo> page = new Page<>(logInfoVo.getPage(),logInfoVo.getLimit());

        QueryWrapper<SysLoginfo> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(logInfoVo.getLoginname()),"loginname",logInfoVo.getLoginname());
        wrapper.like(StringUtils.isNotEmpty(logInfoVo.getLoginip()),"loginip",logInfoVo.getLoginip());
        wrapper.ge(logInfoVo.getStartTime()!=null,"logintime",logInfoVo.getStartTime());
        wrapper.le(logInfoVo.getEndTime()!=null,"logintime",logInfoVo.getEndTime());
        wrapper.orderByDesc("logintime");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.service.page(page,wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 根据id删除单条日志信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteLoginfo")
    public resultObj deleteLoginfo(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("info:delete")){
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
     * 根据id删除多条日志信息
     * @param ids
     * @return
     */
    @RequestMapping("/batchDeleteLoginfo")
    public resultObj batchDeleteLoginfo(Integer[] ids) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("info:batchdelete")){
                //存在
                this.service.removeByIds(Arrays.asList(ids));
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
