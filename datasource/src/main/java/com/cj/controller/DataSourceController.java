package com.cj.controller;

import com.cj.DataSourceType;
import com.cj.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class DataSourceController {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @PostMapping("/dsType")
    public void setDsType(String dsType, HttpSession session){
        // 可以跟当前用户绑定，，存到数据库中
        session.setAttribute(DataSourceType.DS_SESSION_KEY,dsType);
        logger.info("datasource change : "+dsType);

    }

    @Autowired
    UserService userService;
    @GetMapping("/test")
    public void test(){
        userService.getAllUser();
    }
}
