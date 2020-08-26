package com.yzx.layuicms.service;

import com.yzx.layuicms.domain.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 *  SysUserMapper对应的实现接口
 * @author yzx
 * @since 2020-08-11
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 为用户分配角色
     * @param roleId
     * @param userId
     */
    void saveUserRole(@Param("rid")Integer roleId,
                      @Param("uid")Integer userId);

    /**
     * 删除指定用户的角色
     * @param userId
     */
    void deleteUserRole(@Param("uid")Integer userId);

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    Integer findRoleIdByUserId(@Param("uid")Integer userId);

}
