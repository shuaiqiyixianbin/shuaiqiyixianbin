package com.jll.dynamicproxy;

import com.jll.dynamicproxy.proxy.ProxyConfig;
import com.jll.dynamicproxy.proxy.User;
import com.jll.dynamicproxy.proxy.UserMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test")
    public void test(){
        System.out.println("test");
    }



    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(ProxyConfig.class);
        UserMapper user = (UserMapper) annotationConfigApplicationContext.getBean("userMapper");
        System.out.println(user.getUser());

    }


}
