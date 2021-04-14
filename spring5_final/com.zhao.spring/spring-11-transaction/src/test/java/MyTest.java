import com.zhao.mapper.UserMapper;
import com.zhao.mapper.UserMapperImpl;
import com.zhao.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
    @Test
    public void test01(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapperImpl userMapperImpl = context.getBean("userMapperImpl", UserMapperImpl.class);

        List<User> users = userMapperImpl.getUsers();

        for (User user : users) {
            System.out.println(user);
        }
    }

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
