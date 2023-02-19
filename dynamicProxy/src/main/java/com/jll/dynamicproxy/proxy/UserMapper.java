package com.jll.dynamicproxy.proxy;

@Select
public interface UserMapper {

    @Select(value = {
            "select * from cjfmybatistest"
    },suffix = true,suffixSql = "order by id desc")
    public String getUser();

}
