package com.cj.service;

import com.cj.DataSource;
import com.cj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@DataSource("master")
public class UserService {

    @Autowired
    UserMapper userMapper;


    public void getAllUser(){
        System.out.println(userMapper.getAllUser());
    }
}
