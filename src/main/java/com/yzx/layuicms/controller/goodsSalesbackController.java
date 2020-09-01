package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.BusGoods;
import com.yzx.layuicms.domain.BusSales;
import com.yzx.layuicms.domain.BusSalesback;
import com.yzx.layuicms.service.*;
import com.yzx.layuicms.vo.salesbackVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/salesback")
public class goodsSalesbackController {

    @Autowired
    private BusSalesbackService salesbackService;

    @Autowired
    private BusCustomerService customerService;

    @Autowired
    private BusGoodsService goodsService;

    @Autowired
    private BusSalesService salesService;

    /**
     * 加载所有的商品销货退货单信息
     * @param salesbackVo
     * @return
     */
    @RequestMapping("/loadAllSalesback")
    public dataGridView salesback(salesbackVo salesbackVo) { //根据页面传来的参数自动封装一个salesbackVo对象
        //将页面设置的分页信息传入page对象
        IPage<BusSalesback> page = new Page<>(salesbackVo.getPage(),salesbackVo.getLimit());

        QueryWrapper<BusSalesback> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(salesbackVo.getCustomerid() != null,"customerid",salesbackVo.getCustomerid());
        wrapper.like(salesbackVo.getGoodsid() != null,"goodsid",salesbackVo.getGoodsid());
        wrapper.ge(salesbackVo.getStartTime() != null,"salesbacktime",salesbackVo.getStartTime());
        wrapper.le(salesbackVo.getEndTime() != null,"salesbacktime",salesbackVo.getEndTime());
        wrapper.orderByAsc("id");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.salesbackService.page(page,wrapper);

        //获得所有数据
        List<BusSalesback> records = page.getRecords();
        //遍历数据
        for (BusSalesback record : records) {
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
     * 添加商品销货退货单
     * @param id
     * @param salesbackRemark
     * @param salesbackNumber
     * @param operateperson
     * @return
     */
    @RequestMapping("/addSalesback")
    public resultObj addSalesback(Integer id,
                                String salesbackRemark,
                                Integer salesbackNumber,
                                String operateperson){
        try {
            //获取主体对象
            Subject subject = SecurityUtils.getSubject();
            //对主体对象的权限认证，是否有权限进行操作
            if(subject.isPermitted("salesback:create")){
                //存在
                //获取销货信息中的商品实体对象
                BusSales sales = this.salesService.getById(id);
                //创建退货单对象
                BusSalesback salesback = new BusSalesback();
                //设置属性
                salesback.setNumber(salesbackNumber);
                salesback.setGoodsid(sales.getGoodsid());
                salesback.setSalebackprice(sales.getSaleprice());
                salesback.setCustomerid(sales.getCustomerid());
                salesback.setPaytype(sales.getPaytype());
                salesback.setSalesbacktime(sales.getSalestime());
                salesback.setRemark(salesbackRemark);
                salesback.setOperateperson(operateperson);

                //判断退货量是否合理
                if(sales.getNumber() < salesbackNumber){
                    return resultObj.SALESBACKNUMBER_ERROR;
                }

                //更新销货单的销货量，减去退货的数量
                Integer nowNumber = sales.getNumber() - salesbackNumber;
                sales.setNumber(nowNumber);
                this.salesService.updateById(sales);

                //更新商品库存
                //获取当前货单对应商品id
                Integer goodsid = sales.getGoodsid();
                //根据商品id获取商品信息，更新库存
                BusGoods goods = this.goodsService.getById(goodsid);
                int newNumber = goods.getNumber() + salesbackNumber;
                goods.setNumber(newNumber);
                this.goodsService.updateById(goods);

                this.salesbackService.save(salesback);
            }else{
                //不存在
                return resultObj.AUTH_ERROR;
            }
            return resultObj.OUTPORT_SUCCESS;
        }catch (Exception e){
            return resultObj.OUTPORT_ERROR;
        }
    }

}
