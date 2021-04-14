package com.zhao.config;

import com.zhao.pojo.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TestConfigDou.class)
public class TestConfig {

    @Bean
    public User getUser(){
        return new User();
    }

}
