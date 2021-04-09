import com.zhao.pojo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    @Test
    public void testDI(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        Student student = (Student)context.getBean("student");
        Student student1 = context.getBean("student", Student.class);

        System.out.println(student.toString());
        System.out.println(student == student1);
    }
}
