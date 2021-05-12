import com.zhao.pojo.Books;
import com.zhao.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {

    @Test
    public void test1(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        BookService bookServiceImpl = (BookService)applicationContext.getBean("bookServiceImpl");

        for (Books books : bookServiceImpl.queryAllBooks()) {

            System.out.println(books);
        }
    }
}
