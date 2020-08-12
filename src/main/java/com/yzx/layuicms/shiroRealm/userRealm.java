package com.yzx.layuicms.shiroRealm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.layuicms.common.activerUser;
import com.yzx.layuicms.domain.SysUser;
import com.yzx.layuicms.service.SysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * 自定义用户Realm
 */
public class userRealm extends AuthorizingRealm {
    @Autowired
    private SysUserService service;

    //授权方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
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
        SysUser user = service.getOne(wrapper);
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
