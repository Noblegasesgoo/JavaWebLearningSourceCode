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
        <mapper resource="com/zhao/dao/UserMapper.xml"/>
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
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

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

