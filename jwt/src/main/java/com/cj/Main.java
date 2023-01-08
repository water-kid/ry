package com.cj;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class Main {

    private long time = 1000*60*60*24;
    private String signature = "admin";

    @Test
    public void test01(){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder.setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                // 添加载荷
                .claim("username", "tom")
                .claim("role", "admin")
                // 设置主题
                .setSubject("admin-test")
                // 有效时间
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS256, signature)
                // 用 点 拼接起来
                .compact();
        System.out.println("jwtToken = " + jwtToken);
    }

    @Test
    public void test02(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2NzMxNDQyNTcsImp0aSI6ImJhOGM4ZTgyLTkzYzctNGUyNi1iOGYwLTkxMGViMjRlOTZmYiJ9.MYd-a-HETrpWfK4eV9yyvr1t_-x6ESKhVZPwTSSB1Ko";
        JwtParser parser = Jwts.parser();
        // 设置signature
        Jws<Claims> claimsJws = parser.setSigningKey(signature).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }
}
