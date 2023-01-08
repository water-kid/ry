package com.cj.controller;

import com.cj.annotation.RepeatSubmit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RepeatSubmit(interval = 5000,message = "repeat submit。。。")
    @PostMapping("/hello")
    public String hello(@RequestBody String json) {

        return json;
    }

}
