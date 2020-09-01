package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusGoods;
import com.yzx.layuicms.domain.BusSales;
import com.yzx.layuicms.service.BusCustomerService;
import com.yzx.layuicms.service.BusGoodsService;
import com.yzx.layuicms.service.BusSalesService;
import com.yzx.layuicms.service.BusProviderService;
import com.yzx.layuicms.vo.salesVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sales")
public class goodsSalesController {

    @Autowired
    private BusSalesService salesService;

    @Autowired
    private BusCustomerService customerService;

    @Autowired
    private BusGoodsService goodsService;

    /**
     * 加载所有的商品销货单信息
     * @param salesVo
     * @return
     */
    @RequestMapping("/loadAllSales")
    public dataGridView sales(salesVo salesVo) { //根据页面传来的参数自动封装一个salesVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusSales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());

        QueryWrapper<BusSales> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(salesVo.getCustomerid() != null,"customerid",salesVo.getCustomerid());
        wrapper.like(salesVo.getGoodsid() != null,"goodsid",salesVo.getGoodsid());
        wrapper.ge(salesVo.getStartTime() != null,"salestime",salesVo.getStartTime());
        wrapper.le(salesVo.getEndTime() != null,"salestime",salesVo.getEndTime());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.salesService.page(page,wrapper);

        //获得所有数据
        List<BusSales> records = page.getRecords();
        //遍历数据
        for (BusSales record : records) {
            //判断当前销货单单是否有顾客信息
            Integer customerid = record.getCustomerid();
            if(customerid != null){
                //有，则查询顾客名称并设置
                String customername = this.customerService.getById(customerid).getCustomername();
                record.setCustomerName(customername);
            }else{
                record.setCustomerName("暂无顾客信息");
            }

            //判断当前销货单单是否有商品信息
            Integer goodsid = record.getGoodsid();
            if(goodsid != null){
                //有，则查询商品名称以及商品规格并设置
                String goodsname = this.goodsService.getById(goodsid).getGoodsname();
                String goodssize = this.goodsService.getById(goodsid).getSize();
                record.setGoodsName(goodsname);
                record.setSize(goodssize);
            }else{
                record.setGoodsName("未知");
                record.setSize("未知");
            }
        }

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 添加商品销货单
     * @param sales
     * @return
     */
    @RequestMapping("/addSales")
    public resultObj addSales(BusSales sales){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("sales:create")){
                //存在
                //更新商品库存
                //获取当前销货单单新增的货量
                Integer salesNumber = sales.getNumber();
                //获取当前货单对应商品id
                Integer goodsid = sales.getGoodsid();
                //根据商品id获取商品信息，更新库存
                BusGoods goods = this.goodsService.getById(goodsid);
                //判断退货量是否合理
                if(goods.getNumber() < salesNumber){
                    return resultObj.SALESNUMBER_ERROR;
                }
                int newNumber = goods.getNumber() - salesNumber;
                goods.setNumber(newNumber);
                this.goodsService.updateById(goods);

                //获取添加信息中的商品实体对象
                sales.setSalestime(new Date());
                this.salesService.save(sales);
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
     * 更新商品销货单
     * @param sales
     * @return
     */
    @RequestMapping("/updateSales")
    public resultObj updateSales(BusSales sales){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("sales:update")){
                //存在
                this.salesService.updateById(sales);
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
     * 根据id删除单条商品销货单信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteSales")
    public resultObj deleteSales(Integer id) {

        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("sales:delete")){
                //存在

                //更新商品库存
                //根据id获取当前销货单单信息
                BusSales sales = this.salesService.getById(id);
                //获取当前销货单单新增的货量
                Integer salesNumber = sales.getNumber();
                //获取当前货单对应商品id
                Integer goodsid = sales.getGoodsid();
                //根据商品id获取商品信息，更新库存
                BusGoods goods = this.goodsService.getById(goodsid);
                int newNumber = goods.getNumber() + salesNumber;
                goods.setNumber(newNumber);
                this.goodsService.updateById(goods);

                this.salesService.removeById(id);
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
