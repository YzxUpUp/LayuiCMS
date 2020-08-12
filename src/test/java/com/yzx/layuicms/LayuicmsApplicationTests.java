package com.yzx.layuicms;

import com.yzx.layuicms.common.activerUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LayuicmsApplicationTests {

    @Test
    void contextLoads() {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken("jay", "123123"));
        activerUser principal = (activerUser) subject.getPrincipal();
        System.out.println(principal.toString());
    }

}
