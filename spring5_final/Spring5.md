# 1.初次使用spring5：

1.IOC思想的集大成者，控制反转的一个容器。

要使用spring5首先我们得导入spring的包：

## maven配置如下

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

在配置了之后，我们开始使用。

- ### 第一步：创建名为applicationContext的资源文件，将资源文件的框架导入：

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
    <bean class="example.xxx" id="xxx">
        <property name="xxx" value="xxx"/>

        <!-- 在没有无参构造的时候,可以使用下面的方法实现有参构造的参数注入 -->
        <constructor-arg type="example" value="xxx"/>
    </bean>
</beans>
```

- ### 第二步，开始写读取xml文件的核心代码：

  ```java
  Application context = new ClassPathXmlApplicationContext("xxx");//从配置文件中提取出配置上下文信息。
  ```

- ### 第三步，利用junit测试：

  - 一般都是利用context对象来获得实例化对象的。

  ```java
  //获取spring的上下文对象。
  ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
  
  //我们的对象都在spring中来管理了，我们要使用的话直接去spring中去取出来就好了。
  Xxx xxx = (Xxx) context.getBean("xxx");
  xxx.function() 
  ```






## 番外：如何配置有参构造函数？

```xml
        <!-- 方式一：在没有无参构造的时候,可以使用下面的方法实现有参构造的参数注入，不建议使用，因为有两个参数的时候他就不好弄了 -->
        <<constructor-arg type="java.lang.String" value="zlm"/>

        <!-- 方式二：建议使用如下方法 -->
        <constructor-arg index="0" value="zlm"/>

        <!-- 方式三：最直观的方法 -->
        <constructor-arg name="name" value="zlm"/>
```

如果此时我新增一个类，这个类只有无参构造函数，我们在配置文件中配置他：

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
    <bean class="com.zhao.pojo.User" id="user">
        <constructor-arg name="name" value="zlm"/>
    </bean>

    <!-- 新增User2类的配置 -->
    <bean class="com.zhao.pojo.User2" id="user2"/>
</beans>
```

再次运行我们测试程序段：

```java
import com.zhao.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testWhereAreTheObjectFrom(){
        //获取spring的上下文对象。
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //我们的对象都在spring中来管理了，我们要使用的话直接去spring中去取出来就好了。
        User user = (User) context.getBean("user");
        user.show();
    }
}
```

会得如下结果：

```cmd
User2的无参构造
name:zlm

Process finished with exit code 0
```

==这表明，只要在配置文件中配置了类，在ApplicationContext对象创建时，那么即使你不用他，他也会被spring创建对象。==

## 总结：

- 在配置文件加载的时候，容器中被管理的对象就已经被创建了。

# 2、spring的配置

## 2.1 别名（alias了解）

现在配置中申请User类的别名userAlias：

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
    <bean class="com.zhao.pojo.User" id="user">
        <constructor-arg name="name" value="zlm"/>
    </bean>

    <alias name="user" alias="userAlias"/>
</beans>
```

此时在我们的测试类里运用user和别名userAlias来分别创建一个对象并且比较他们的方法输出，以及地址是否指向同一个对象：

```java
import com.zhao.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testWhereAreTheObjectFrom(){
        //获取spring的上下文对象。
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //我们的对象都在spring中来管理了，我们要使用的话直接去spring中去取出来就好了。
        User user1 = (User) context.getBean("user");
        User user2 = (User) context.getBean("userAlias");
        user1.show();
        user2.show();

        System.out.println(user1 == user2);
    }
}
```

结果：

```cmd
User2的无参构造
name:zlm
name:zlm
true

Process finished with exit code 0
```

总结：

- 别名这种东西，他真的就是起了一个别名而已，多了个名字。
- 如果以后很多user，那么你就可以写别名。



## 2.2 Bean的配置（掌握）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 使用spring来创建我们的对象，spring中对象都为bean
       	bean就相当于一个对象。

       	id == 对象名。
       	class == new的对象的全限定名称（包名+类名）。
		name == 别名，而且是比alias更高级的。
       	property == 给对象中的属性设置一个值,可以同时起多个别名，中间用，或者空格隔开。
		
    -->
    
    <bean id="user2" class="com.zhao.pojo.User2" name="user2Alias1 user2Alias2,user2Alias3"/>
</beans>
```

测试：

```java
import com.zhao.pojo.User;
import com.zhao.pojo.User2;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testWhereAreTheObjectFrom(){
        //获取spring的上下文对象。
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //我们的对象都在spring中来管理了，我们要使用的话直接去spring中去取出来就好了。
        User2 user21 = (User2)context.getBean("user2");
        User2 user22 = (User2)context.getBean("user2Alias1");
        User2 user23 = (User2)context.getBean("user2Alias2");
        User2 user24 = (User2)context.getBean("user2Alias3");
        
        user21.show();
        user22.show();
        user23.show();
        user24.show();
        
        System.out.println(user23 == user24);
        System.out.println(user22 == user23);
        System.out.println(user21 == user22);
    }
}

```

结果：

```cmd
User2的无参构造
name:null
name:null
name:null
name:null
true
true
true

Process finished with exit code 0
```





## 2.3 导入（import）

- import一般都是团队的时候使用，它可以将多个配置文件合成一个配置文件来使用。

  

  假设现在我们有四个人同时工作，写了四个不同的配置文件，而我们的ApplicationContext对象只想从一个配置文件中同时使用这四个配置文件，

  那么我们就可以把其中三个文件==import==到剩下的一个文件中：

  这里我们将beans.xml、beans2.xml、bean3.xml三个配置文件导入applicationContext.xml文件中：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans
          http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <import resource="beans.xml"/>
      <import resource="beans2.xml"/>
      <import resource="bean3.xml"/>
  
  </beans>
  ```

  我们在测试类中只通过applicationContext.xml文件来获得ApplicationContext的对象，这样下来：

  ```java
  import com.zhao.pojo.User;
  import com.zhao.pojo.User2;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class MyTest {
      @Test
      public void testWhereAreTheObjectFrom(){
          //获取spring的上下文对象。
          ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  
          User2 user21 = (User2)context.getBean("user2");
          User2 user22 = (User2)context.getBean("user2Alias1");
          User2 user23 = (User2)context.getBean("user2Alias2");
          User2 user24 = (User2)context.getBean("user2Alias3");
  
          user21.show();
          user22.show();
          user23.show();
          user24.show();
  
          System.out.println(user23 == user24);
          System.out.println(user22 == user23);
          System.out.println(user21 == user22);
      }
  }
  
  ```

  我们发现运行结果与2.2的运行结果一样，所以我们可以知道合并成功了：

  ```
  User2的无参构造
  name:null
  name:null
  name:null
  name:null
  true
  true
  true
  
  Process finished with exit code 0
  ```

  

# 3、依赖注入

## 3.1 构造器注入

- 第一节的番外就讲了。

## 3.2 Set方式注入==（重点）==

- 依赖注入：set注入。
  - 依赖：bean对象的创建依赖于容器。
  - 注入：bean对象中的所有属性由容器来注入。

==1.测试环境搭建==

Student类：

```java
package com.zhao.pojo;

import java.util.*;

public class Student {
    //搭建复杂环境,几乎涵盖了所有依赖注入的类型:
    private String name;
    private StudentAddress address;
    private String[] books;
    private List<String> hobbies;
    private Map<String,String> cards;
    private Set<String> games;
    private Properties info;
    private String wife;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StudentAddress getAddress() {
        return address;
    }

    public void setAddress(StudentAddress address) {
        this.address = address;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    public String getWife() {
        return wife;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", books=" + Arrays.toString(books) +
                ", hobbies=" + hobbies +
                ", cards=" + cards +
                ", games=" + games +
                ", info=" + info +
                ", wife='" + wife + '\'' +
                '}';
    }
}
```



StudentAddress类：

```java
package com.zhao.pojo;

public class StudentAddress {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```



开始配置XML，XML里的所有配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="com.zhao.pojo.StudentAddress">
        <property name="address" value="DLNU"/>
     </bean>

    <bean id="student" class="com.zhao.pojo.Student">
        <!-- 第一种注入方法：普通值的注入，用value -->
        <property name="name">
            <value>zlm</value>
        </property>

        <!-- 第二种注入方法：bean注入，用ref -->
        <property name="address" ref="address"/>

        <!-- 数组注入 -->
        <property name="books">
            <array>
                <value>Ori</value>
                <value>red and black</value>
            </array>
        </property>

        <!-- List注入 -->
        <property name="hobbies">
            <list>
                <value>singing</value>
                <value>sleeping</value>
                <value>eating</value>
            </list>
        </property>

        <!-- Map注入 -->
        <property name="cards">
            <map>
                <entry key="ID" value="1111111"/>
                <entry key="Card" value="1234 2423"/>
                <entry key="TEL" value="12312412124"/>
            </map>
        </property>

        <!-- Set注入 -->
        <property name="games">
            <set>
                <value>MHW</value>
                <value>Mario</value>
                <value>COD</value>
            </set>
        </property>

        <!-- null注入 -->
        <!-- <property name="wife" value=""/>-->
        <property name="wife">
            <null/>
        </property>

        <!-- Properties注入 -->
        <property name="info">
            <props>
                <prop key="No">2018250829</prop>
                <prop key="Sex">M</prop>
            </props>
        </property>
    </bean>

</beans>
```



# 4、Bean的作用域



## 4.1 单例模式（spring的默认机制，单线程一般使用这个模式）

- 无论你为某个类创建多少个bean，创建出来的实例依旧是==单一的一个==，全局共享一个。

- 单例模式是默认模式。

  ```xml
  <!-- 以下是实现模式 --> 
  <bean id="student" class="com.zhao.pojo.Student" scope="singleton">
  ```



## 4.2 原型模式（多线程一般使用这个模式）

- 你为一个类创建几个bean那你就创建了几个对象。

- 每次从容器中get的时候，都会产生新的对象。

  ```xml
  <!-- 以下是实现模式 --> 
  <bean id="student" class="com.zhao.pojo.Student" scope="prototype">
  ```



## 4.3 其余的还有Session、Request、Application模式

- 这些只能在web开发时使用到。
- Application的对象，会在全局都活着，其他俩的只会活在自己的作用域中。



# 5、Bean的自动装配

- Bean的自动装配是Spring满足bean依赖的一种实现方式。
- Spring会在上下文中自动寻找，并且会去自动装配属性。

- 在XML中显示配置（和上面一样）。
- 在java中显示装配。
- ==隐式自动装配==





## 5.1 测试自动装配



### 5.1.1  测试环境

准备工作，三个类，一个类中有另外两个类的属性：

```java
package com.zhao.pojo;

public class Man {
    private String name;
    private Dog dog;
    private Cat cat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Man{" +
                "name='" + name + '\'' +
                ", dog=" + dog +
                ", cat=" + cat +
                '}';
    }
}
```



xml中显示配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dog" class="com.zhao.pojo.Dog"/>
    <bean id="cat" class="com.zhao.pojo.Cat"/>

    <bean id="man" class="com.zhao.pojo.Man">
        <property name="name" value="zlm"/>
        <property name="cat" ref="cat"/>
        <property name="dog" ref="dog"/>
    </bean>
</beans>
```



测试+结果：

```java
import com.zhao.pojo.Man;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Man man = context.getBean("man", Man.class);

        man.getCat().call();
        man.getDog().call();
        System.out.println(man.getName());
    }
}
```

```cmd
miao~~~~~
wang~~~
zlm

Process finished with exit code 0
```



### 5.1.2 ByName自动装配

- 他会自动在容器上下文中找和自己对象的set方法中对应的bean的id，根据属性名和id自动装配。

在xml中的具体配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="dog" class="com.zhao.pojo.Dog"/>
    <bean id="cat" class="com.zhao.pojo.Cat"/>

    <!-- 自动装配的关键字autowried！！！-->
    <bean id="man" class="com.zhao.pojo.Man" autowire="byName" >
        <property name="name" value="zlm"/>
        <!-- 
			这里的set方法中的beanid = dog，相当于以下这句:
			<property name="dog" value="dog"/> 
		-->
    </bean>
</beans>
```

此时我们运行结果和上面一样，所以我们自动装配成功了！

但是要注意：

- ==我们可以通过别名的方式来完成对byname的自动装配。==

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
 
    <!-- 差别在我这里虽然id为dog11但是我起了一个别名叫dog，所以byname的自动装配能够识别得到 -->
    <bean id="dog11" class="com.zhao.pojo.Dog" name="dog"/>
   
    <bean id="cat" class="com.zhao.pojo.Cat"/>

    <bean id="man" class="com.zhao.pojo.Man" autowire="byName" >
        <property name="name" value="zlm"/>
    </bean>
</beans>
```



### 5.1.3 ByType自动装配

- 他会自动在容器上下文中找和自己对象的属性类型相同的bean，必须要保证全局类型唯一。

在xml中的具体配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 
		这里我用的就是和set中beanid不同的id，但是我用bytype自动装配它可以自动识别类型装配，
		而且我连id都不用了都可以实现自动装配。
	-->
    <bean id="dog11" class="com.zhao.pojo.Dog"/>
    <bean class="com.zhao.pojo.Cat"/>

    <bean id="man" class="com.zhao.pojo.Man" autowire="byType" >
        <property name="name" value="zlm"/>
    </bean>
</beans>
```

注意！这个类型匹配不是万能的，如果我有多个dog的bean或者多个cat的bean的话就不能装配成功了！

## 5.2 小结

- ByName自动装配的时候，我们确保所有要自动装配的bean的id唯一，并且是和需要自动注入的属性的set方法的值的类型一致。
- ByType自动装配的时候，我们确保所有要自动装配的bean的class唯一，并且和自己对象的属性类型相同。

# 6、使用注解实现自动装配



## 6.1 使用步骤

- 导入使用注解的约束（context）以及注解的支持：

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

- 测试：

  ==我们将@AutoWired注解放到要自动装配的属性上（也可以放到要自动装配的set方法上，一般放到属性上即可）==：

  ```java
  public class Man {
  
      private String name;
      @Autowired
      private Dog dog;
      @Autowired
      private Cat cat;
      
      ...
  }
  ```

  运行测试类及结果 标志着自动装配成功：

  ```java
  import com.zhao.pojo.Man;
  import org.junit.Test;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  public class MyTest {
      @Test
      public void test01(){
          ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  
          Man man = context.getBean("man", Man.class);
  
          man.getCat().call();
          man.getDog().call();
      }
  }
  ```

  ```cmd
  miao~~~~~
  wang~~~
  
  Process finished with exit code 0
  ```

  

## 6.2 @AutoWired注解

- ==一般@AutoWired注解放到要自动装配的属性上（也可以放到要自动装配的set方法上，一般放到属性上即可）。==
- 用了该注解注解了属性之后，甚至我们都不用设置set方法了！！！**前提是你的要自动装配属性在ioc容器中存在且名字符合ByType**。

## 6.3 拓展

- @NotNull注解：被该注解标注的字段属性可以为null。

- @AutoWired注解的required属性的运用，只要这个属性你给他赋值为false那么，可以允许没在容器中的bean的类为空。

  也就是说允许被标记属性没有在容器中配置bean。

  ```java
  package org.springframework.beans.factory.annotation;
  
  import java.lang.annotation.Documented;
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  
  @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface Autowired {
      boolean required() default true;
  }
  
  ```
  - @Qualifier注解：在不满足**@AutoWired**注解的**ByType**下，我们可以用该注解/==@Qualifie(value = "xxx")==来实现注解完成**ByName**自动装配的任务。

    换句话说就是**@AutoWired**和**@Qualifier**注解功能就很像**@Resource**注解。

    

  ## 6.4 总结

  - 单独使用**@AutoWired**注解一般都是一个类只配了一个bean，如果一个类同时配置了多个不同的bean的话，类型重复了，就配合**@Qualifier**注解来指定你要自动装配的那个bean的id。



# 7、spring的注解开发

## 7.1 准备

- 先确保**spring**的**AOP**包依赖导入了。
- 之后**applicationContext.xml**文件中添加对注解支持的**context**了。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 提供注解支持 -->
    <context:annotation-config/>
    <!-- 扫描指定包下的注解 -->
    <context:component-scan base-package="xxx.xxx.xxx"/>
    
</beans>
```



## 7.2 各种注解 (如果稍微复杂的bean还是最好使用xml去配置，更快更清晰)

>  **@Component注解**：自动帮你创建一个该注解标注下的类的bean。被这个注解标志的类代表该类被spring管理了。
>
> ​								   它会告诉spring被他标记的类要作为一个组件并且需要创建一个bean
>
> ​									这个注解相当于applicationContext中的：
>
> ```xml
> <bean id="user" class="com.zhao.pojo.User"/>，
> ```
>
> 
>
> **@Value注解**：spring中的注解，这个注解可以向下面这样给被托管的类的属性赋值相当于，也可以把它用在对应字段的Set方法上：
>
> ```xml
> <bean id="user" class="com.zhao.pojo.User">
>     <property name="name" value="zlm"/>
> </bean>
> ```



```java
package com.zhao.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class User {
    @Value("zlm")
    public String name;
}


```



## 7.3 一些衍生注解

> 以下几个注解都是和@Component注解一样将他们标注的类注册到spring容器中去
>
> - @Controller
> - @Repository
> - @Service

> - @Scope可以看作是设置设计模式的注解。
>
> ```java
> package com.zhao.pojo;
> 
> import org.springframework.beans.factory.annotation.Value;
> import org.springframework.context.annotation.Scope;
> import org.springframework.stereotype.Component;
> 
> //这个注解相当于applicationContext中的：<bean id="user" class="com.zhao.pojo.User"/>
> @Component
> @Scope("singleton")
> public class User {
>     @Value("zlm")
>     public String name;
> 	
> 	...
> }
> 
> ```
>
> 



# 8、XML与注解开发的一些对比

- xml开发：
  - 逻辑条理清晰，对于复杂项目的时候比较容易维护。
- 注解开发：
  - ==需要在xml中提供注解的支持。==
  - 不复杂项目可以使用，但是复杂项目用注解会增大维护难度。



总结：

- 我们可以用xml文件来管理Bean。
- 在使用xml文件的基础上用注解来完成属性的注入。



# 9、运用JavaConfig实现配置

> 对应官方文档1.12

- **第一步：**我们建立一个即将放到Spring容器中的类User：

  ```java
  package com.zhao.pojo;
  
  import org.springframework.beans.factory.annotation.Value;
  import org.springframework.stereotype.Component;
  
  //这里不加@Component注解的原因是在配置类中我们即将注册该类的bean！！！！！！！！
  public class User {
      private String name;
  
      public User() {
      }
  
      public User(String name) {
          this.name = name;
      }
  
      public String getName() {
          return name;
      }
  
      @Value("zlm")//注入后的初值 
      public void setName(String name) {
          this.name = name;
      }
  
      @Override
      public String toString() {
          return "User{" +
                  "name='" + name + '\'' +
                  '}';
      }
  }
  ```

  

- **第二步：**先建立一个config包，这个包下的文件都是属于你要配置给spring的：

  我在这个包下创建了一个TestConfig类：

  ```java
  package com.zhao.config;
  
  import com.zhao.pojo.User;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.context.annotation.Import;
  
  @Configuration
  @Import(TestConfigDou.class)//将另一个配置类导入合并为一个文件。
  public class TestConfig {
      /*
      	这里被bean注解的方法：
      		这个方法的名字 == xml中bean标签中的id
      		这个方法的返回值 == xml中bean标签中的class
      */
      @Bean
      public User getUser(){
          return new User();//返回要注入bean的对象
      }
  
  }
  ```

  这个被@Bean注解标记的方法等同于我们原来xml文件中的这句话：

  ```xml
  <bean id="user" class="com.zhao.pojo.User"/>
  ```

  ==注意==：

  - 这里的**@Configuration**注解代表了它也被Spring托管了并且注册到了容器中，为什么呢？因为：

    ```java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Component //他被@Component注解标注了！！！！
    public @interface Configuration {...}
    ```

- ==小贴士：如果要让Spring不用xml文件托管组件类的话：==

  - 方法一：

    ​	首先新建一个配置类，并且用**@Configruation**注解标注该类。

    ​	然后在配置类中定义一个要自动装配的类的方法，用**@Bean**注解标注。

  - 方法二：

    ​	找到你要托管的组件类，用**@Component**注解标注该类。

    ​	并且在类上在使用**@ComponentScan("该类的路径")**标注该类，

    ​	这样Spring会自动扫描**@Component**注解标注的类并且生成对应的bean。



# 10、aop实现



## 10.1 Spring-API实现AOP

> 主要是SpringAPI接口的实现。

xml配置如下：

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
    <!-- 哪个接口需要被代理，就把哪个接口的实现类注册为bean -->
    <bean id="userServiceImpl" class="com.zhao.service.UserServiceImpl"/>
    <bean id="log" class="com.zhao.log.Log"/>
    <bean id="afterLog" class="com.zhao.log.AfterLog"/>

    <!-- 配置aop:导入aop的约束 -->

    <!-- 方式一：使用原生的Spring API接口-->
    <aop:config>
        <!-- 切入点： expression表达式：修饰词 返回值 列名 方法名 参数-->
        <aop:pointcut id="pointcut" expression="execution(* com.zhao.service.UserServiceImpl.* (.. ))"/>

        <!-- 执行环绕增加:添加要切入的东西 -->
        <aop:advisor advice-ref="log" pointcut-ref="pointcut"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut"/>

    </aop:config>

</beans>
```

测试类：

```java
import com.zhao.service.UserService;
import com.zhao.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理代理的是接口不是实现类！！！
        UserService userService = context.getBean("userServiceImpl", UserService.class);

        userService.add();
    }
}
```



## 10.2 自定义类来实现AOP

> 主要是切面的定义。

切入点类：

```java
package com.zhao.diy;

//方式二：自定义切面类。
//切面说白了就是一个类。
public class DIYPointCut {
    public void before(){
        System.out.println("方法执行之前。");
    }

    public void after(){
        System.out.println("方法执行之后。");

    }
}
```



xml配置文件：

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
    <!-- 哪个接口需要被代理，就把哪个接口的实现类注册为bean -->
    <bean id="userServiceImpl" class="com.zhao.service.UserServiceImpl"/>
    <bean id="diy" class="com.zhao.diy.DIYPointCut"/>


    <!-- 配置aop:导入aop的约束 -->
    <!-- 方式二：自定义类来实现AOP -->
   
    <aop:config>
        <!-- 自定义切面（aspect）： ref：切面要引用的类 -->
        <aop:aspect ref="diy">
            <!-- 切入点和通知构成切面 -->
            
            
            <!-- 定义切面的切入点（pointcut） -->
            <aop:pointcut id="pointCut" expression="execution(* com.zhao.service.UserServiceImpl.* (..))"/>
            
            <!-- 通知：在哪执行？（before/after） 切入点在哪？-->
            <aop:before method="before" pointcut-ref="pointCut"/>
            <aop:after method="after" pointcut-ref="pointCut"/>
            
        </aop:aspect>
    </aop:config>
</beans>
```

测试类：

```java
import com.zhao.service.UserService;
import com.zhao.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理代理的是接口不是实现类！！！
        UserService userService = context.getBean("userServiceImpl", UserService.class);

        userService.add();
    }
}
```



## 10.3 注解实现aop

xml配置方式一（不自动装配bean）：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 方式三:注解实现aop -->
    <!-- 注册bean -->
    <!-- 如果我们不想手动注册的话还可以使用@Component注解自动代理 -->
    <bean id="userServiceImpl" class="com.zhao.service.UserServiceImpl"/>
    <bean id="annotationPointCut" class="com.zhao.diy.AnnotationPointCut"/>

    <!-- 开启注解支持 -->
    <aop:aspectj-autoproxy/>

</beans>
```

xml配置方式二（自动装配bean）:

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

</beans>
```

切面类：

```java
package com.zhao.diy;

//方式三：注解实现aop

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//被@Aspect注解标注代表这个类是一个切面。
//在xml文件中就会被自动装配为bean切面。
@Component
@Aspect
public class AnnotationPointCut {

    //被@Before注解标记的方法就可以理解为通知。
    @Before("execution(* com.zhao.service.UserServiceImpl.* (..))")
    public void before(){
        System.out.println("注解实现aop：方法执行前。");
    }

    @After("execution(* com.zhao.service.UserServiceImpl.* (..))")
    public void after(){
        System.out.println("注解实现aop：方法执行后。");
    }
}
```

被代理类：

```java
package com.zhao.service;

import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("增加");
    }

    @Override
    public void delete() {
        System.out.println("删除");
    }

    @Override
    public void update() {
        System.out.println("更新");
    }

    @Override
    public void query() {
        System.out.println("查询");
    }
}
```

测试类：

```java
import com.zhao.service.UserService;
import com.zhao.service.UserServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理代理的是接口不是实现类！！！
        UserService userService = context.getBean("userServiceImpl", UserService.class);

        userService.add();
    }
}
```



# 11、整合mybatis

步骤：

>  导入jar包。	

- mybatis包。
- junit包。
- mysql数据库包。
- spring相关包。
- aop织入包。
- mybatis-spring包。



> 编写配置文件。



> 测试。

## 11.1 回忆mybatis：

> 步骤

- 编写实体类。
- 编写mybatis核心配置文件。
- 编写接口。
- 编写**Mapper**.**xml**。
- 测试。



## 11.2  ==Mybatis-Spring：==

- 第一步：配置数据源。
- 第二步：注册**mybatis**必须的**SqlSessionFactory**对象到容器中去。
- 第三步：注册等效于我们原来**mybatis**提供的**SqlSessionFactory**类的另一个**spring**提供的类**SqlSessionTemplate**类。
- 第四步：创建一个实现接口实现类，并且注册到容器中来

> 具体实现过程以及需要的类如下:
>
> - 一个Spring整合mybatis的xml
> - 一个你要做事情的mapper接口
> - 一个这个mapper接口的实现类
> - 一个这个mapper接口的mybatis的xml操作文件

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
    <context:component-scan base-package="com.xxx.xxx"/>
    <context:component-scan base-package="com.xxx.xxx"/>

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

    <!-- *** 如果用了SqlSessionDaoSupport类实现整合的话，可以省略第三步 ***-->
    <!-- 第三步：  创建等效于我们原来的sqlSessionFactory对象的另一个spring提供的类 -->
    <!-- SqlSessionTemplate类就是我们原来的SqlSessionFactory -->
    <bean id="sqlSessionTemp" class="org.mybatis.spring.SqlSessionTemplate">
        <!-- 只能使用构造器注入，因为没有set方法 -->
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>


    <!-- 第四步:  创建一个实现接口实现类，并且注册到容器中来 -->
    <bean id="userMapperImpl" class="com.zhao.mapper.UserMapperImpl">
        <property name="sqlSessionTemplate" ref="sqlSessionTemp"/>
    </bean>

</beans>
```

### 实现方式一：运用**SqlSessionTemplate**

```java
package com.zhao.mapper;

import com.zhao.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

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

实现方式二：运用**SqlSessionDaoSupport**

```java
package com.zhao.mapper;

import com.zhao.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper {
    /*
    	对比第一种方法，我们简略的省略了第一种方式中的以下几步：
    	
            //我们的所有操作，原来都是用sqlSession执行，现在我们用spring整合mybatis之后的sqlSessionTemplate来实现。

            private SqlSessionTemplate sqlSessionTemplate;

            //给spring注入设置一个set方法。
            public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
                this.sqlSessionTemplate = sqlSessionTemplate;
            }
    */
    @Override
    public List<User> getUsers() {
        return getSqlSession().getMapper(UserMapper.class).getUsers();
    }
}

```



# 12、声明式事务

## 12.1 回顾事务：

- 原子性，把一组业务当成一个业务来做。
- 项目开发中十分重要。
- 涉及到了数据的一致性问题。
- 确保完整性和一致性。



## 12.2 事务的ACID原则：

- 原子性、一致性、隔离性、持久性。



## 12.3 如何使用spring进行事务的管理：

- 声明式事务：

  - 一般用aop来实现，不影响源代码。

- 编程式事务：

  - 需要在代码中进行事务的管理。

  

### 12.3.1 声明式事务的实现

- 配置文件：

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



假设弄出来的事务：

```java
package com.zhao.mapper;

import com.zhao.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{

    //随便弄的一个事务方法。
    @Override
    public List<User> getUsers() {
        User user = new User(20, "kjb", "21142");
        addUser(user);
        deleteUser(20);
        
        return getSqlSession().getMapper(UserMapper.class).getUsers();
    }

    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(20);
    }
}
```

测试类：

```java
import com.zhao.mapper.UserMapper;
import com.zhao.mapper.UserMapperImpl;
import com.zhao.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
	@Test
    public void testTransaction(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //我们在aop织入事务之后，这里不应该是 实现类 而是 接口。
        //因此我们将这个地方改成UserMapper.class就会成功了。
        UserMapper userMapperImpl = context.getBean("userMapperImpl", UserMapper.class);

        List<User> users = userMapperImpl.getUsers();

        for (User user : users) {
            System.out.println(user);
        }
    }
}

```



