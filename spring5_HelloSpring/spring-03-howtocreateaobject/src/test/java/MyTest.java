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
