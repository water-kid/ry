package com.cj.controller;

import com.cj.annotation.DataScope;
import com.cj.entity.Dept;
import com.cj.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author cc
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    IDeptService deptService;

    @DataScope(deptAlias = "d")
    @GetMapping
    public List<Dept> getAllDepts(Dept dept){
        return deptService.getAllDepts(dept);
    }

}
