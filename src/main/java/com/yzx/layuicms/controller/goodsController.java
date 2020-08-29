package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusGoods;
import com.yzx.layuicms.domain.BusProvider;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.BusGoodsService;
import com.yzx.layuicms.service.BusProviderService;
import com.yzx.layuicms.vo.goodsVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class goodsController {

    @Autowired
    private BusGoodsService goodsService;

    @Autowired
    private BusProviderService providerService;

    /**
     * 加载所有的商品信息
     * @param goodsVo
     * @return
     */
    @RequestMapping("/loadAllGoods")
    public dataGridView goods(goodsVo goodsVo) { //根据页面传来的参数自动封装一个goodsVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusGoods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());

        QueryWrapper<BusGoods> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getGoodsname()),"goodsname",goodsVo.getGoodsname());
        wrapper.like(goodsVo.getProviderid() != null,"providerid",goodsVo.getProviderid());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getProductcode()),"productcode",goodsVo.getProductcode());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getPromitcode()),"promitcode",goodsVo.getPromitcode());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getDescription()),"description",goodsVo.getDescription());
        wrapper.like(StringUtils.isNotEmpty(goodsVo.getSize()),"size",goodsVo.getSize());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.goodsService.page(page,wrapper);

        //获得所有数据
        List<BusGoods> records = page.getRecords();
        //遍历数据
        for (BusGoods record : records) {
            //判断当前商品是否有供应商
            Integer providerid = record.getProviderid();
            if(providerid != null){
                //有，则查询供应商名称并设置
                String providername = this.providerService.getById(providerid).getProvidername();
                record.setProviderName(providername);
            }else{
                record.setProviderName("暂无供应商");
            }
        }

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加商品
     * @param goods
     * @return
     */
    @RequestMapping("/addGoods")
    public resultObj addGoods(BusGoods goods){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("goods:create")){
                //存在
                this.goodsService.save(goods);
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
     * 更新商品
     * @param goods
     * @return
     */
    @RequestMapping("/updateGoods")
    public resultObj updateGoods(BusGoods goods){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("goods:update")){
                //存在
                this.goodsService.updateById(goods);
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
     * 根据id删除单条商品信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteGoods")
    public resultObj deleteGoods(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("goods:delete")){
                //存在
                this.goodsService.removeById(id);
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
     * 加载所有商品的名称
     *
     * @return
     */
    @RequestMapping("/loadGoodsName")
    public dataGridView loadGoodsName() {
        QueryWrapper<BusGoods> wrapper = new QueryWrapper<>();
        wrapper.select("id","goodsname");
        List<BusGoods> allUserName = this.goodsService.list(wrapper);
        return new dataGridView(allUserName);
    }

}
