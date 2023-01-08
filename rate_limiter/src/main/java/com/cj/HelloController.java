package com.cj;

import com.cj.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RateLimiter(time = 10,count = 3,limitType = LimitType.IP)
    @GetMapping("/hello")
    public String hello() {

        return "hello";
    }

}
