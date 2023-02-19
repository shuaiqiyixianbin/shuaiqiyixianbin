package com.jll.dynamicproxy.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.sql.*;

@Slf4j
@Component
public class ProxyFactoryBean<T> implements FactoryBean<T>, InvocationHandler, InitializingBean, ApplicationContextAware, EnvironmentAware {


    private Class<T> type;

    // 返回new出来的代理对象
    @Override
    public T getObject() throws Exception {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{getObjectType()},this);
    }

    @Override
    public Class<T> getObjectType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    // proxy是代理对象,生成的代理对象
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("进入mapperScanner方法");
        // 拿到mapper的方法
        //Method method1 = proxy.getClass().getInterfaces()[0].getMethod(method.getName(),null);
        method.setAccessible(true);
        Select select = method.getDeclaredAnnotation(Select.class);
        String sql = select.value()[0];
        String realSql = null;
        boolean suffix = select.suffix();
        String suffixSql = select.suffixSql();
        //拼接在from后面
        if (suffix){
            realSql = sql + " "+ suffixSql;
        }
        log.info("realSql:[{}]", realSql);

        StringBuffer sb = new StringBuffer();

        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://47.101.219.60:3306/cjftest";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "123456";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();

            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(realSql);
            while (rs.next()) {
                //获取stuname这列数据
                String id = "id:" + rs.getInt("id") + " ";
                //获取stuid这列数据
                String name = "name:" + rs.getString("name") + " ";
                sb.append(id + name);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException e) {
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private ApplicationContext applicationContext;

    private String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    

}
