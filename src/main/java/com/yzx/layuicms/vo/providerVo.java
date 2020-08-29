package com.yzx.layuicms.vo;

import com.yzx.layuicms.domain.BusCustomer;
import com.yzx.layuicms.domain.BusProvider;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 装载从页面传来数据的实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class providerVo extends BusProvider {

    private static final long serialVersionUID = 1L;

    //和页面开启分页功能的表格搭配使用，接受参数进行分页
    private Integer page;
    private Integer limit;

    //装载多个id实现批量删除
    private Integer[] ids;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}
