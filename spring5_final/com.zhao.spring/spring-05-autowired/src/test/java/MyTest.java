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
