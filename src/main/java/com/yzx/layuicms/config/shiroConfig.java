package com.yzx.layuicms.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yzx.layuicms.shiroRealm.userRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro的配置类
 */
@Configuration
public class shiroConfig {

    /**
     * 获取生成shiro过滤器的工厂类
     * @return
     */
    @Bean(name="shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //设置敏感资源和公共资源
        Map<String,String> map = new HashMap<>();
        map.put("/layuicms2.0/**","anon");
        map.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //设置默认返回地址
        shiroFilterFactoryBean.setLoginUrl("/");
        //设置认证成功返回地址
        shiroFilterFactoryBean.setSuccessUrl("/index");

        return shiroFilterFactoryBean;
    }

    /**
     * 获取web项目使用的shiro安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("getRealm") Realm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager =
                new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    /**
     * 获取自定义Realm
     * @return
     */
    @Bean
    public Realm getRealm() {
        userRealm uRealm = new userRealm();
        //创建新的凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密方式为mds
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列值为1024
        credentialsMatcher.setHashIterations(1024);
        //设置该匹配器为自定义Realm的匹配器
        uRealm.setCredentialsMatcher(credentialsMatcher);
        return uRealm;
    }

    /**
     * 使得shiro可以和thymeleaf搭配使用
     * @return
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

}
