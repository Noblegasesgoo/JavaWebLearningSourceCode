package com.zhao.mapper;

import com.zhao.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper {

    /**
     * 添加一本书。
     * @param books
     * @return 成功返回1。
     */
    public Integer addBook(Books books);


    /**
     * 通过书的id删除一本书。
     * @param id
     * @return 成功返回1。
     */
    public Integer deleteBookById(@Param("bookID") Integer id);

    /**
     * 更新某一本书的信息。
     * @param books
     * @return 成功返回1。
     */
    public Integer updateBook(Books books);

    /**
     * 根据id查询一本书。
     * @param id
     * @return 返回查询到的这本书。
     */
    public Books queryBookById(@Param("bookID") Integer id);

    /**
     * 查询所有的书的信息。
     * @return 所有书的列表。
     */
    public List<Books> queryAllBooks();

}
