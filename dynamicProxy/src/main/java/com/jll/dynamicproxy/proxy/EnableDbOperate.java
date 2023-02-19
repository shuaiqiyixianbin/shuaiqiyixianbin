package com.jll.dynamicproxy.proxy;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MyImportBeanDefinitionRegister.class)
public @interface EnableDbOperate {
}
