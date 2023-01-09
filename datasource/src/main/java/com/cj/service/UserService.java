package com.cj.service;

import com.cj.DataSource;
import com.cj.mapper.UserMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@DataSource("master")
public class UserService {

    @Autowired
    UserMapper userMapper;


    public void getAllUser(){
        System.out.println(userMapper.getAllUser());
    }

    @Autowired
    MasterService masterService;


    @Autowired
    SlaveService slaveService;

    @GlobalTransactional
    public void test(){
        masterService.updateUser("aaa",1);
//       int  i = 1/0;
        slaveService.updateUser("bbb",9);
    }
}
