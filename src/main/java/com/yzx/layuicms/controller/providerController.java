package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusProvider;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.BusProviderService;
import com.yzx.layuicms.vo.providerVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class providerController {

    @Autowired
    private BusProviderService providerService;

    /**
     * 加载所有的供应商信息
     * @param providerVo
     * @return
     */
    @RequestMapping("/loadAllProvider")
    public dataGridView provider(providerVo providerVo) { //根据页面传来的参数自动封装一个providerVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusProvider> page = new Page<>(providerVo.getPage(),providerVo.getLimit());

        QueryWrapper<BusProvider> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(providerVo.getProvidername()),"providername",providerVo.getProvidername());
        wrapper.like(StringUtils.isNotEmpty(providerVo.getTelephone()),"telephone",providerVo.getTelephone());
        wrapper.like(StringUtils.isNotEmpty(providerVo.getConnectionperson()),"connectionperson",providerVo.getConnectionperson());
        wrapper.like(StringUtils.isNotEmpty(providerVo.getAddress()),"address",providerVo.getAddress());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.providerService.page(page,wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    @RequestMapping("/addProvider")
    public resultObj addProvider(BusProvider provider){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("provider:create")){
                //存在
                this.providerService.save(provider);
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
     * 更新供应商
     * @param provider
     * @return
     */
    @RequestMapping("/updateProvider")
    public resultObj updateProvider(BusProvider provider){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("provider:update")){
                //存在
                this.providerService.updateById(provider);
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
     * 根据id删除单条供应商信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteProvider")
    public resultObj deleteProvider(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("provider:delete")){
                //存在
                this.providerService.removeById(id);
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
     * 加载所有供应商的名称
     *
     * @return
     */
    @RequestMapping("/loadProviderName")
    public dataGridView loadProviderName() {
        QueryWrapper<BusProvider> wrapper = new QueryWrapper<>();
        wrapper.select("id","providername");
        List<BusProvider> allUserName = this.providerService.list(wrapper);
        return new dataGridView(allUserName);
    }

}
