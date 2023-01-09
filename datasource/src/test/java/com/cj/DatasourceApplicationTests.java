package com.cj;

import com.cj.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatasourceApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        userService.getAllUser();
    }


    @Test
    public void test01(){
        userService.test();
    }

}
