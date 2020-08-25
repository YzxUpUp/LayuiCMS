package com.yzx.layuicms.mapper;

import com.yzx.layuicms.domain.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzx
 * @since 2020-08-24
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据roleId获取对应的权限和菜单id
     * @param roleId
     * @return
     */
    List<Integer> getPermissionIdByRoleId(@Param("rid")Integer roleId);

    /**
     * 添加对应角色的权限信息
     * @param roleId
     * @param pid
     */
    void saveRolePermission(@Param("rid")Integer roleId,
                            @Param("pid")Integer pid);

    /**
     * 根据roleId删除所有权限信息
     * @param roleId
     */
    void deleteRolePermission(@Param("rid")Integer roleId);

}
