package com.cj.service.impl;

import com.cj.entity.Role;
import com.cj.mapper.RoleMapper;
import com.cj.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2023-01-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> getAllRoles(Role role) {
        return roleMapper.getAllRoles(role);
    }
}
