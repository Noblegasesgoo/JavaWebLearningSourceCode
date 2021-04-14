## LOG4J的配置文件简要：

```properties
log4j.properties
#将等级为DEBUG的日志信息输出到console和file两个目的地
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.Target=System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关配置
log4j.appender.file=org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/kuang.log
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}[%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

## MySQL的配置文件简要：

```properties
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306?useSSL=true&useUnicode=true&characterEncoding=UTF8
username=root
password=mysql123456
```

## 解决MySQL的时间戳的问题：

```cmd
连接数据库 mysql -hlocalhost -uroot -p，回车，输入密码，回车
继续输入 show variables like'%time_zone';  
输入set global time_zone = '+8:00'; 
```

## mybatis-config的配置文件简要：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 引入外部配置文件 -->
    <properties resource="db.properties"/>

    <!-- 使用日志工厂 -->
    <settings>
        <!-- 标准的日志工厂的使用 -->
<!--        <setting name="logImpl" value="STDOUT_LOGGING"/>-->
        <setting name="logImpl" value="LOG4J"/>
    </settings>

    <!-- 为一些实体类写别名 -->
    <typeAliases>
        <typeAlias type="com.zhao.pojo.User" alias="User"/>
        <!-- <package name="com.zhao.pojo"/> 这样的，他会自动扫描符合的javabean，并且会以该bean的首字母小写名称来起该bean的名称
            你可以在实体类上运用@Alias注解来为实体类起别名，这样运用这种方式扫描到的javabean的别名就是注解中的名字了。
        -->
    </typeAliases>

    <environments default="test">
        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>

    <mappers>
        <mapper resource="UserMapper.xml"/>
        <!-- <mapper class="com.zhao.dao.UserMapper"/>
         <package name="com.zhao.dao"/>
        使用class和package方式。
        注意点：
            配置文件（.xml文件）必须和要实现的mapper接口同名。
            配置文件（.xml文件）必须和要实现的mapper接口同包。
        -->
    </mappers>

</configuration>
```

## Mybtatis中Mapper.xml配置文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!-- 一个命名空间（namespace）绑定一个对应的dao/mapper接口-->
<mapper namespace="xxx.xxx.xxx.xxx">

    <!-- namespace：对应java的一个DAO接口 -->
    <!-- id：对应namespace绑定的DAO接口中的方法名称 -->
    <!-- resultType：对应id绑定的namespace中的方法的返回值/sql语句的返回值 -->

</mapper>
```

## Mybatis的工具类一般模板：

```java
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
```

## 防止mybatis读取不到资源的语句：

```xml
<build>
        <!-- 配置resource来防止我们的资源导出失败的问题 -->
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>

            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>

        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.4.2</version>
                <configuration>
                    <skipTests>true</skipTests>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

## Git的配置文件：

```iml
*.class
*.log
*.lock

# Package Files #
*.jar
*.war
*.ear
target/

# idea
.idea/
*.iml/

*velocity.log*

### STS ###
.apt_generated
.factorypath
.springBeans

### IntelliJ IDEA ###
*.iml
*.ipr
*.iws
.idea
.classpath
.project
.settings/
bin/

*.log
tem/

#rebel
*rebel.xml*
```

## 2020IDEA解决maven子工程无法使用父工程的包的问题：

```java
//Setting -> Maven -> Runner -> Delegate IDE build/run action to Maven
```



![image-20210324000710836](C:\Users\noblegasesgoo\AppData\Roaming\Typora\typora-user-images\image-20210324000710836.png)



# 有关Spring5的内容

## Spring5框架运用于web的要maven导入的包：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.3.5</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.3.5</version>
</dependency>

```

## Spring5框架中applicationContext.xml的配置模板：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd

        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd

        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 方式三:注解实现aop -->
    <!-- 注册bean -->

    <!-- 提供注解支持 -->
    <context:annotation-config/>
    <!-- 扫描指定包下的注解 -->
    <context:component-scan base-package="com.zhao.service"/>
    <context:component-scan base-package="com.zhao.diy"/>

    <!-- 开启注解支持 -->
    <aop:aspectj-autoproxy/>
    
    <!-- 使用spring来创建我们的对象，spring中对象都为bean
       bean就相当于一个对象。

       这里的id相当于对象名。
       这里的class就是你要new的对象的类。
       property相当于给对象中的属性设置一个值。
    -->

</beans>
```

## Spring5框架中注解支持的模板：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>

</beans>
```

## Spring5框架中aop支持的模板：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 注册bean -->
	<!-- 配置aop:导入aop的约束 -->
</beans>
```

## Spring5中对mybatis整合的XML配置文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd

        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd

        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 方式三:注解实现aop -->
    <!-- 注册bean -->

    <!-- 提供注解支持 -->
    <context:annotation-config/>
    <!-- 扫描指定包下的注解 -->
    <context:component-scan base-package="com.zhao.service"/>
    <context:component-scan base-package="com.zhao.diy"/>

    <!-- 开启注解支持 -->
    <aop:aspectj-autoproxy/>

    <!-- 第一步：  配置数据源（DataSources）
        将mybatis的配置替换为spring的数据源：c3p0 dbcp ...
    -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF8"/>
        <property name="username" value="root"/>
        <property name="password" value="mysql123456"/>
    </bean>

    <!-- 第二步：  注册SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>

        <!-- 绑定mybatis配置文件:
                此时mybatis就和spring连起来了，我们可以去mybatis-config.xml文件中去拓展功能。
         -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/zhao/mapper/UserMapper.xml"/>
    </bean>

    <!-- 第三步：  创建等效于我们原来的sqlSessionFactory对象的另一个spring提供的类 -->
    <!-- SqlSessionTemplate类就是我们原来的SqlSessionFactory -->
    <bean id="sqlSessionTemp" class="org.mybatis.spring.SqlSessionTemplate">
        <!-- 只能使用构造器注入，因为没有set方法 -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>


	<!-- 非必选 -->
    <!-- 第四步:  创建一个实现接口实现类，并且注册到容器中来 -->
    <bean id="userMapperImpl" class="com.zhao.mapper.UserMapperImpl">
        <property name="sqlSessionTemplate" ref="sqlSessionTemp"/>
    </bean>

</beans>
```

## Spring5中对mybatis整合后实现类的模板：

```java
public class UserMapperImpl implements UserMapper{

    //我们的所有操作，原来都是用sqlSession执行，现在我们用spring整合mybatis之后的sqlSessionTemplate来实现。

    private SqlSessionTemplate sqlSessionTemplate;

    //给spring注入设置一个set方法。
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> getUsers() {
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        return mapper.getUsers();
    }
}
```

## Spring5中对应的mybatis整合事务的框架类型模板：

spring-dao.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd

        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd

        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd

        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 方式三:注解实现aop -->
    <!-- 注册bean -->

    <!-- 提供注解支持 -->
    <context:annotation-config/>
    <!-- 扫描指定包下的注解 -->
    <!--    <context:component-scan base-package="com.zhao.service"/>-->
    <!--    <context:component-scan base-package="com.zhao.diy"/>-->

    <!-- 开启注解支持 -->
    <aop:aspectj-autoproxy/>
    
    <!-- ============================================== 分界线 ======================================================== -->

    <!-- 第一步：  配置数据源（DataSources）
        将mybatis的配置替换为spring的数据源：c3p0 dbcp ...
    -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF8"/>
        <property name="username" value="root"/>
        <property name="password" value="mysql123456"/>
    </bean>

    <!-- 第二步：  注册SqlSessionFactory对象 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>

        <!-- 绑定mybatis配置文件:
                此时mybatis就和spring连起来了，我们可以去mybatis-config.xml文件中去拓展功能。
         -->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:com/zhao/mapper/UserMapper.xml"/>
    </bean>

    <!-- 第三步：  创建等效于我们原来的sqlSessionFactory对象的另一个spring提供的类 -->
    <!-- SqlSessionTemplate类就是我们原来的SqlSessionFactory -->
    <bean id="sqlSessionTemp" class="org.mybatis.spring.SqlSessionTemplate">
        <!-- 只能使用构造器注入，因为没有set方法 -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>

    <!-- ============================================== 分界线 ======================================================== -->

    <!-- 配置声明式事务 -->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <constructor-arg name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 结合aop实现事务的支持 -->
    <!-- 配置事务通知 -->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <!-- 给哪些方法配置事务 -->
        <!-- *可以代表所有方法 -->
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!-- 配置事务切入 -->
     <aop:config>
         <aop:pointcut id="txPointCut" expression="execution(* com.zhao.mapper.*.*(..))"/>
         <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
     </aop:config>
</beans>
```

applicationContext.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"

       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd

        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd

        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 方式三:注解实现aop -->
    <!-- 注册bean -->

    <!-- 提供注解支持 -->
    <context:annotation-config/>
    <!-- 扫描指定包下的注解 -->
    <!--    <context:component-scan base-package="com.zhao.service"/>-->
    <!--    <context:component-scan base-package="com.zhao.diy"/>-->

    <!-- 开启注解支持 -->
    <aop:aspectj-autoproxy/>

    <import resource="spring-dao.xml"/>

    <bean id="userMapperImpl" class="com.zhao.mapper.UserMapperImpl">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>

</beans>
```





