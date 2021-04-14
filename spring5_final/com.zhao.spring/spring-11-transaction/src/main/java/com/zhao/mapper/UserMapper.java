package com.zhao.mapper;

import com.zhao.pojo.User;

import java.util.List;

public interface UserMapper {
    public List<User> getUsers();

    //添加一组事务。
    public int addUser(User user);
    public int deleteUser(int id);
}
