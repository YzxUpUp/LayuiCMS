package com.yzx.layuicms.service.impl;

import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.mapper.SysUserMapper;
import com.yzx.layuicms.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 *  SysUserMapper对应的实现接口的实现类
 * @author yzx
 * @since 2020-08-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    /**
     * 为用户分配角色
     * @param roleId
     * @param userId
     */
    @Override
    public void saveUserRole(Integer roleId, Integer userId) {
        this.getBaseMapper().saveUserRole(roleId,userId);
    }

    /**
     * 删除指定用户的角色
     * @param userId
     */
    @Override
    public void deleteUserRole(Integer userId) {
        this.getBaseMapper().deleteUserRole(userId);
    }

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    @Override
    public Integer findRoleIdByUserId(Integer userId) {
        return this.getBaseMapper().findRoleIdByUserId(userId);
    }

}
