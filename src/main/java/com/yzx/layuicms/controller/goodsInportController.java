package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusGoods;
import com.yzx.layuicms.domain.BusInport;
import com.yzx.layuicms.service.BusGoodsService;
import com.yzx.layuicms.service.BusInportService;
import com.yzx.layuicms.service.BusProviderService;
import com.yzx.layuicms.vo.inportVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/inport")
public class goodsInportController {

    @Autowired
    private BusInportService inportService;

    @Autowired
    private BusProviderService providerService;

    @Autowired
    private BusGoodsService goodsService;

    /**
     * 加载所有的商品进货信息
     * @param inportVo
     * @return
     */
    @RequestMapping("/loadAllInport")
    public dataGridView inport(inportVo inportVo) { //根据页面传来的参数自动封装一个inportVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusInport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());

        QueryWrapper<BusInport> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(inportVo.getProviderid() != null,"providerid",inportVo.getProviderid());
        wrapper.like(inportVo.getGoodsid() != null,"goodsid",inportVo.getGoodsid());
        wrapper.ge(inportVo.getStartTime() != null,"inporttime",inportVo.getStartTime());
        wrapper.le(inportVo.getEndTime() != null,"inporttime",inportVo.getEndTime());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.inportService.page(page,wrapper);

        //获得所有数据
        List<BusInport> records = page.getRecords();
        //遍历数据
        for (BusInport record : records) {
            //判断当前进货单是否有供应商信息
            Integer providerid = record.getProviderid();
            if(providerid != null){
                //有，则查询供应商名称并设置
                String providername = this.providerService.getById(providerid).getProvidername();
                record.setProviderName(providername);
            }else{
                record.setProviderName("暂无供应商");
            }

            //判断当前进货单是否有商品信息
            Integer goodsid = record.getGoodsid();
            if(providerid != null){
                //有，则查询商品名称以及商品规格并设置
                String goodsname = this.goodsService.getById(goodsid).getGoodsname();
                String goodssize = this.goodsService.getById(goodsid).getSize();
                record.setGoodsName(goodsname);
                record.setSize(goodssize);
            }else{
                record.setProviderName("未命名");
            }
        }

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加商品进货
     * @param inport
     * @return
     */
    @RequestMapping("/addInport")
    public resultObj addInport(BusInport inport){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("inport:create")){
                //存在
                //获取添加信息中的商品实体对象
                BusGoods goodbyid = this.goodsService.getById(inport.getGoodsid());
                if(!goodbyid.getProviderid().equals(inport.getProviderid())){
                    return resultObj.PROVIDER_ERROR;
                }
                inport.setInporttime(new Date());
                this.inportService.save(inport);
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
     * 更新商品进货
     * @param inport
     * @return
     */
    @RequestMapping("/updateInport")
    public resultObj updateInport(BusInport inport){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("inport:update")){
                //存在
                this.inportService.updateById(inport);
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
     * 根据id删除单条商品进货信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteInport")
    public resultObj deleteInport(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("inport:delete")){
                //存在
                this.inportService.removeById(id);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return resultObj.DELETE_ERROR;
        }

    }

    @RequestMapping("/cutBackNumber")
    public void cutBackNumber(Integer outputNumber,
                              Integer id){
        BusGoods goods = this.goodsService.getById(id);
        Integer haveNumber = goods.getNumber();
        Integer nowHaveNumber = haveNumber - outputNumber;
        goods.setNumber(nowHaveNumber);
    }

}
