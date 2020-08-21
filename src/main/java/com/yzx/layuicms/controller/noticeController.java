package com.yzx.layuicms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.layuicms.common.dataGridView;
import com.yzx.layuicms.common.resultObj;
import com.yzx.layuicms.domain.SysLoginfo;
import com.yzx.layuicms.domain.SysNotice;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysNoticeService;
import com.yzx.layuicms.vo.logInfoVo;
import com.yzx.layuicms.vo.noticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Date;

@RestController
@RequestMapping("/notice")
public class noticeController {

    @Autowired
    private SysNoticeService service;

    /**
     * 加载全部公告信息
     * @param noticeVo
     * @return
     */
    @RequestMapping("/loadAllNotice")
    public dataGridView notice(noticeVo noticeVo) {
        //将页面设置的分页信息传入page对象
        IPage<SysNotice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());

        QueryWrapper<SysNotice> wrapper = new QueryWrapper<>();
        //当判断条件存在，才会设置筛选条件
        wrapper.like(StringUtils.isNotEmpty(noticeVo.getTitle()), "title", noticeVo.getTitle());
        wrapper.like(StringUtils.isNotEmpty(noticeVo.getOpername()), "opername", noticeVo.getOpername());
        wrapper.ge(noticeVo.getStartTime() != null, "createtime", noticeVo.getStartTime());
        wrapper.le(noticeVo.getEndTime() != null, "createtime", noticeVo.getEndTime());
        wrapper.orderByDesc("createtime");

        //使用服务类根据page对象和筛选条件进行分页数据查找
        this.service.page(page, wrapper);

        //以查询到的所有数据树和数据本身作为参数，创建新的dataGridView对象返回JSON字符串
        return new dataGridView((long) page.getRecords().size(), page.getRecords());
    }

    /**
     * 根据id更新单条公告信息
     * @param id
     * @param content
     * @return
     */
    @RequestMapping("/updateNotice")
    public resultObj updateNotice(Integer id, String content) {
        try {
            //首先根据id获取对应公告对象
            SysNotice oldNotice = this.service.getById(id);
            //为其设置新的内容
            oldNotice.setContent(content);
            //更新数据库中该公告的信息
            this.service.updateById(oldNotice);
            return resultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return resultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据id删除单条公告信息
     * @param id
     * @return
     */
    @RequestMapping("/deleteNotice")
    public resultObj deleteNotice(Integer id) {
        try {
            this.service.removeById(id);
            return resultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return resultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据id删除多条公告信息
     * @param ids
     * @return
     */
    @RequestMapping("/batchDeleteNotice")
    public resultObj batchDeleteNotice(Integer[] ids) {
        try {
            this.service.removeByIds(Arrays.asList(ids));
            return resultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return resultObj.DELETE_ERROR;
        }
    }

    /**
     * 添加一条公告
     * @param noticeVo
     * @return
     */
    @RequestMapping("/addNotice")
    public resultObj addNotice(noticeVo noticeVo,
                               HttpSession session) {
        try {
            //设置创建事件
            noticeVo.setCreatetime(new Date());
            //从session域中获取当前登录账户的用户名信息
            SysUser user = (SysUser) session.getAttribute("user");
            //设置用户名
            noticeVo.setOpername(user.getName());
            //以该实体类为蓝本添加新的公告
            this.service.save(noticeVo);
            return resultObj.ADD_SUCCESS;
        } catch (Exception e){
            return resultObj.ADD_ERROR;
        }
    }

}
