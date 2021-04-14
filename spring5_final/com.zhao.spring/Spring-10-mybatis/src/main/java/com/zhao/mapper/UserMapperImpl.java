package com.zhao.mapper;

import com.zhao.pojo.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{

    //我们的所有操作，原来都是用sqlSession执行，现在我们用spring整合mybatis之后的sqlSessionTemplate来实现。

    private SqlSessionTemplate sqlSessionTemplate;

    //给spring注入设置一个set方法。
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public List<User> getUsers() {
        UserMapper mapper = sqlSessionTemplate.getMapper(UserMapper.class);
        return mapper.getUsers();
    }
}
