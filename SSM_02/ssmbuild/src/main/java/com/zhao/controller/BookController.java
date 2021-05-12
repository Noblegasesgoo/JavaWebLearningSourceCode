package com.zhao.controller;

import com.zhao.pojo.Books;
import com.zhao.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    //controller层调用service层

    //自动装配注入一下BookService
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;


    @RequestMapping("/allbooks")
    public String bookList(Model model){

        List<Books> books = bookService.queryAllBooks();

        model.addAttribute("books", books);

        return "allbooks";
    }

    //跳转到书籍添加页面
    @RequestMapping("/toaddbooks")
    public String toAddPaper(){
        //跳转到书籍添加页面
        return "addbooks";
    }

    //添加书籍
    @RequestMapping("/addbooks")
    public String addBook(Books books) {
        //添加书籍
        int i = bookService.addBook(books);

        //后台查看是否添加成功
        System.out.println(i);

        return "redirect:/book/allbooks";
    }

    //跳转到修改页面
    @RequestMapping("/toupdatebooks")
    public String toUpdatePaper(int id, Model model){
        //我们要从前端获取并且接收这个id，之后再通过这个id查到这本书。
        Books books = bookService.queryBookById(id);

        //根据id查到这本书之后，将这本书的信息反馈给前端显示，然后你就可以开始修改了。
        model.addAttribute("qBooks",books);
        return "updatebooks";
    }

    //修改书籍
    @RequestMapping("/updatebooks")
    public String updateBook(Books books ) {
        //打印当前修改的书籍是哪本。
        System.out.println("updateBook => " + books);

        //将我们前端页面修改过的数值的book接收回来并且以这本book的参数进行书籍信息的修改。
        bookService.updateBook(books);

        return "redirect:/book/allbooks";//重定向到@RequestMapping("/allbooks")请求。
    }

    //删除书籍，不用页面所以不用写跳转。
    //我们复习一下啊restful风格。
    @RequestMapping("/deletebooks/{bookID}")
    public String deleteBook(@PathVariable("bookID") int id) {
        bookService.deleteBookById(id);
        return "redirect:/book/allbooks";
    }

    //

}
