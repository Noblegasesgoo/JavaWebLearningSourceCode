package com.zhao.service;

import com.zhao.mapper.BookMapper;
import com.zhao.pojo.Books;

import java.util.List;

public class BookServiceImpl implements BookService{

    //service层调用mapper层。
    private BookMapper bookMapper;

    //设置get/set方法方便spring托管。
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public BookMapper getBookMapper() {
        return bookMapper;
    }

    /**
     * 添加一本书。
     * @param books
     * @return 成功返回1。
     */
    @Override
    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    /**
     * 通过书的id删除一本书。
     * @param id
     * @return 成功返回1。
     */
    @Override
    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    /**
     * 更新某一本书的信息。
     * @param books
     * @return 成功返回1。
     */
    @Override
    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    /**
     * 根据id查询一本书。
     * @param id
     * @return 返回查询到的这本书。
     */
    @Override
    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }

    /**
     * 查询所有的书的信息。
     * @return 所有书的列表。
     */
    @Override
    public List<Books> queryAllBooks() {
        return bookMapper.queryAllBooks();
    }
}
