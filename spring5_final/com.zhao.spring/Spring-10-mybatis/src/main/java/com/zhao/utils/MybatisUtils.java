package com.zhao.utils;

//Mybatis工具类

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/*
    该工具类的作用：
        将xml资源文件加载进来，创建一个可以执行sql的对象。
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    //使用Mybatis的第一步。
    //这个静态代码块就是获取sqlSessionFactory对象。
    static {
        try {
            //下面三句话是写死了的
            //第一个就是从xml文件中提取配置文件信息
            String resource = "mybatis-config.xml";
            //将配置文件信息放入流中
            InputStream inputStream = Resources.getResourceAsStream(resource);
            //最后构建sqlSessionFactory工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //第二步。
    //有了SqlSessionFactory之后我们就可以来获得SqlSession实例了！
    //SqlSession给我们提供了在数据库中执行SQL命令的所有方法，你可以通过 SqlSession 实例来直接执行已映射的 SQL 语句。
    public static SqlSession getSqlSession(){
        //工厂生产一个sqlSession出来
        return sqlSessionFactory.openSession();
    }
}
