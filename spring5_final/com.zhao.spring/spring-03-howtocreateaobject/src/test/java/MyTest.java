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

        //我们的对象都在spring中来管理了，我们要使用的话直接去spring中去取出来就好了。
//        User user1 = (User) context.getBean("user");
//        User user2 = (User) context.getBean("userAlias");
//        user1.show();
//        user2.show();
//
//        System.out.println(user1 == user2);

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
