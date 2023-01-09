package com.cj.service;

import com.cj.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlaveService {

    @Autowired
    SlaveMapper slaveMapper;

    @DataSource("slave")
    public void updateUser(String username,Integer id){
        slaveMapper.updateUser(username,id);
//        int i = 1/0;
    }
}
