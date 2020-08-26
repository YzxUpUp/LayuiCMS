package com.yzx.layuicms.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * sys_user表对应实体类
 * @author yzx
 * @since 2020-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptid;

    @TableField(exist = false)
    private String deptName;

    private Date hiredate;

    private Integer mgr;

    @TableField(exist = false)
    private String mgrName;

    private Integer available;

    private Integer ordernum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgpath;

    private String salt;


}
