import com.zhao.config.TestConfig;
import com.zhao.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MyTest {
    @Test
    public void test(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);

        User user = (User) context.getBean("getUser");

        System.out.println(user.getName());

    }
}
