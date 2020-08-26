package com.yzx.layuicms.mapper;

import com.yzx.layuicms.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 *  sys_user对应Mapper 接口
 * @author yzx
 * @since 2020-08-11
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

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
