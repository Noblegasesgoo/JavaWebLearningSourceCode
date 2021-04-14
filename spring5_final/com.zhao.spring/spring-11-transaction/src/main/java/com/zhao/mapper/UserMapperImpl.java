package com.zhao.mapper;

import com.zhao.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

public class UserMapperImpl extends SqlSessionDaoSupport implements UserMapper{

    @Override
    public List<User> getUsers() {
        User user = new User(20, "kjb", "21142");
        addUser(user);
        deleteUser(20);

        return getSqlSession().getMapper(UserMapper.class).getUsers();
    }

    @Override
    public int addUser(User user) {
        return getSqlSession().getMapper(UserMapper.class).addUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return getSqlSession().getMapper(UserMapper.class).deleteUser(20);
    }
}
