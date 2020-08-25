package com.yzx.layuicms.service.impl;

import com.yzx.layuicms.domain.SysRole;
import com.yzx.layuicms.mapper.SysRoleMapper;
import com.yzx.layuicms.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzx
 * @since 2020-08-24
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    /**
     * 根据roleId获取对应的权限和菜单id
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> getPermissionIdByRoleId(Integer roleId) {
        return this.getBaseMapper().getPermissionIdByRoleId(roleId);
    }

    /**
     * 修改对应角色的权限信息
     * @param roleId
     * @param pid
     */
    @Override
    public void saveRolePermission(Integer roleId, Integer pid) {
        this.getBaseMapper().saveRolePermission(roleId,pid);
    }

    /**
     * 根据roleId删除所有权限信息
     * @param roleId
     */
    @Override
    public void deleteRolePermission(Integer roleId) {
        this.getBaseMapper().deleteRolePermission(roleId);
    }

}
