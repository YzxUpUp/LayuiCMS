package com.yzx.layuicms.service;

import com.yzx.layuicms.domain.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzx
 * @since 2020-08-24
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据roleId获取对应的权限和菜单id
     * @param roleId
     * @return
     */
    List<Integer> getPermissionIdByRoleId(@Param("roleId")Integer roleId);

    /**
     * 修改对应角色的权限信息
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
