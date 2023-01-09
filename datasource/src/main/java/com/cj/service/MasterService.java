package com.cj.service;

import com.cj.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterService {
    @Autowired
    MasterMapper masterMapper;

    @DataSource("master")
    public void updateUser(String username,Integer id){
         masterMapper.updateUser(username,id);
    }

}
