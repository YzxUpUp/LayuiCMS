package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusGoods;
import com.yzx.layuicms.domain.BusInport;
import com.yzx.layuicms.domain.BusOutport;
import com.yzx.layuicms.service.BusGoodsService;
import com.yzx.layuicms.service.BusInportService;
import com.yzx.layuicms.service.BusOutportService;
import com.yzx.layuicms.service.BusProviderService;
import com.yzx.layuicms.vo.outportVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/outport")
public class goodsOutportController {

    @Autowired
    private BusOutportService outportService;

    @Autowired
    private BusProviderService providerService;

    @Autowired
    private BusGoodsService goodsService;

    @Autowired
    private BusInportService inportService;

    /**
     * 加载所有的商品进货退货单信息
     * @param outportVo
     * @return
     */
    @RequestMapping("/loadAllOutport")
    public dataGridView outport(outportVo outportVo) { //根据页面传来的参数自动封装一个outportVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusOutport> page = new Page<>(outportVo.getPage(),outportVo.getLimit());

        QueryWrapper<BusOutport> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(outportVo.getProviderid() != null,"providerid",outportVo.getProviderid());
        wrapper.like(outportVo.getGoodsid() != null,"goodsid",outportVo.getGoodsid());
        wrapper.ge(outportVo.getStartTime() != null,"outporttime",outportVo.getStartTime());
        wrapper.le(outportVo.getEndTime() != null,"outporttime",outportVo.getEndTime());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.outportService.page(page,wrapper);

        //获得所有数据
        List<BusOutport> records = page.getRecords();
        //遍历数据
        for (BusOutport record : records) {
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
     * 添加商品进货退货单
     * @param outportId
     * @param outportRemark
     * @param number
     * @param operateperson
     * @return
     */
    @RequestMapping("/addOutport")
    public resultObj addOutport(Integer id,
                                String outportRemark,
                                Integer outputNumber,
                                String operateperson){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("outport:create")){
                //存在
                //获取添加信息中的商品实体对象
                BusInport inport = this.inportService.getById(id);
                //创建退货单对象
                BusOutport outport = new BusOutport();
                //设置属性
                outport.setNumber(outputNumber);
                outport.setGoodsid(inport.getGoodsid());
                outport.setOutportprice(inport.getInportprice());
                outport.setProviderid(inport.getProviderid());
                outport.setPaytype(inport.getPaytype());
                outport.setOutputtime(inport.getInporttime());
                outport.setRemark(outportRemark);
                outport.setOperateperson(operateperson);

                this.outportService.save(outport);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.OUTPORT_SUCCESS;
        }catch (Exception e){
            return resultObj.OUTPORT_ERROR;
        }
    }

    /**
     * 更新商品进货退货单
     * @param outport
     * @return
     */
    @RequestMapping("/updateOutport")
    public resultObj updateOutport(BusOutport outport){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("outport:update")){
                //存在
                this.outportService.updateById(outport);
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
     * 根据id删除单条商品进货退货单信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteOutport")
    public resultObj deleteOutport(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("outport:delete")){
                //存在
                this.outportService.removeById(id);
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
