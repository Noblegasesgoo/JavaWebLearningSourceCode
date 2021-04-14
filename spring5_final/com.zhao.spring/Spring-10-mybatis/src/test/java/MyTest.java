import com.zhao.mapper.UserMapper;
import com.zhao.mapper.UserMapperImpl;
import com.zhao.mapper.UserMapperImpl2;
import com.zhao.pojo.User;
import com.zhao.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class MyTest {
    @Test
    public void testGetUsers() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapperImpl userMapperImpl = context.getBean("userMapperImpl", UserMapperImpl.class);

        List<User> users = userMapperImpl.getUsers();

        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void testUserMapperImpl2(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapperImpl2 userMapperImpl2 = context.getBean("userMapperImpl2", UserMapperImpl2.class);

        List<User> users = userMapperImpl2.getUsers();

        for (User user : users) {
            System.out.println(user);
        }
    }
}
