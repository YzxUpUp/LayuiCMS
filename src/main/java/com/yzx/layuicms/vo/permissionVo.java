package com.yzx.layuicms.vo;

import com.yzx.layuicms.domain.SysPermission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 装载从页面传来数据的实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class permissionVo extends SysPermission {

    private static final long serialVersionUID = 1L;

}
