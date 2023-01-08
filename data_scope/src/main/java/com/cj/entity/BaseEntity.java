package com.cj.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.HashMap;
import java.util.Map;

public class BaseEntity {
    // 需要追加的sql  ,, data_scope是key ，，value是自定义的sql语句，，数据库中data_scope字段值不同，生成sql不同
    @TableField(exist = false)
    private Map<String,String> params = new HashMap<>();

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
