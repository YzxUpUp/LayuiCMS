package com.yzx.layuicms.common;

import com.yzx.layuicms.domain.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 当前用户实体类
 * 装载当前登录用户的信息、角色和权限
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class activerUser {
    //用户
    private SysUser user;
    //角色
    private List<String> roles;
    //权限
    private List<String> pers;
}
