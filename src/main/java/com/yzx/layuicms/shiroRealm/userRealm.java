package com.yzx.layuicms.shiroRealm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.common.constant;
import com.yzx.layuicms.domain.SysPermission;
import com.yzx.layuicms.domain.SysRole;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysPermissionService;
import com.yzx.layuicms.service.SysRoleService;
import com.yzx.layuicms.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义用户Realm
 */
public class userRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysPermissionService permissionService;

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登录用户实体类
        activerUser primaryPrincipal = (activerUser) principals.getPrimaryPrincipal();
        SysUser user = primaryPrincipal.getUser();
        //根据用户id找到对应的角色id
        Integer roleIdByUserId = this.userService.findRoleIdByUserId(user.getId());
        //根据角色id找到对应的权限id集合
        List<Integer> permissionIdByRoleId = this.roleService.getPermissionIdByRoleId(roleIdByUserId);
        //根据权限id找到权限对应的权限标识符集合
        QueryWrapper<SysPermission> permissionQueryWrapper = new QueryWrapper<>();
        permissionQueryWrapper.select("percode");
        permissionQueryWrapper.in("id",permissionIdByRoleId);
        permissionQueryWrapper.eq("type", constant.TYPE_PERMISSION);
        List<SysPermission> list = this.permissionService.list(permissionQueryWrapper);
        List<String> perCodes = new ArrayList<>();
        for (SysPermission sysPermission : list) {
            perCodes.add(sysPermission.getPercode());
        }
        //根据身份信息，设置不同的角色或资源访问权限
        //创建权限设置器
        SimpleAuthorizationInfo authorizationInfo =
                new SimpleAuthorizationInfo();
        //设置角色信息或者资源访问权限
        //设置资源访问权限，例如user:*:01、guest:01:*、admin:*:*……
        authorizationInfo.addStringPermissions(perCodes);
        //返回该设置器，表示对当前主体对象的权限设置
        return authorizationInfo;
    }

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名信息
        String principal = (String) token.getPrincipal();
        //创建筛选条件对象
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        //设置筛选条件
        wrapper.eq("loginname",principal);
        //根据筛选条件查找
        SysUser user = userService.getOne(wrapper);
        //判断查找到的对象是否为空
        if(!ObjectUtils.isEmpty(user)){
            //不为空，创建当前对象
            activerUser activerUser = new activerUser();
            //添加查找到的用户对象为当前对象
            activerUser.setUser(user);
            //将对应字符串转换为盐值信息
            ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
            //进行登录验证
            return new SimpleAuthenticationInfo(
                    activerUser,
                    user.getPwd(),
                    byteSource,
                    this.getName());
        }
        return null;
    }
}
