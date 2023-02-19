package com.jll.dynamicproxy.proxy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface Select {

    /**
     * 具体要执行的sql
     * @return
     */
    String[] value() default {};

    /**
     * 是否添加后缀
     * @return
     */
    boolean suffix() default false;

    /**
     * 强制走索引名称
     * @return
     */
    String suffixSql() default "";

}
