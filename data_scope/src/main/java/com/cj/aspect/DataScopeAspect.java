package com.cj.aspect;

import com.cj.annotation.DataScope;
import com.cj.entity.BaseEntity;
import com.cj.entity.Role;
import com.cj.entity.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class DataScopeAspect {

    private static final String DATA_SCOPE_ALL="1";
    private static final String DATA_SCOPE_CUSTOM="2";
    private static final String DATA_SCOPE_DEPT="3";
    private static final String DATA_SCOPE_DEPT_AND_CHILD="4";
    private static final String DATA_SCOPE_SELF="5";


    private static final String DATA_SCOPE = "data_scope";

    @Before("@annotation(dataScope)")
    public void before(JoinPoint joinPoint, DataScope dataScope){
        // 清空params中的参数，，防止前端传入params，，sql注入
        Object arg = joinPoint.getArgs()[0];
        if (arg != null && arg instanceof BaseEntity){
            BaseEntity baseEntity = (BaseEntity) arg;
            // 将params清空
            baseEntity.getParams().put(DATA_SCOPE,"");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 超级管理员，不用权限过滤
        if (user.getUserId()==1L) {
            return;
        }

        // 要追加的sql
        StringBuilder sql = new StringBuilder();

        // select * from dept where deptId in (xxx   or  xxx  or  xxx) ,,,,一个role对应一个xxx
        // select * from sys_dept where dept_id in (100) or dept_id in(105)
        List<Role> roleList = user.getRoleList();
        for (Role role : roleList) {
            // 数据库中 data_scope
            String ds = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(ds)){
                return;
            }else if (DATA_SCOPE_CUSTOM.equals(ds)){
                // 自定义数据权限，， 就根据用户角色查找部门id        %s:字符    %d：数字
                sql.append(String.format(" OR  %s.dept_id in (select rd.dept_id from sys_role_dept rd where rd.role_id=%d)",dataScope.deptAlias(),role.getRoleId()));
            }else if (DATA_SCOPE_DEPT.equals(ds)){
                // sys_user 表中 dept_id字段，，当前用户所在部门id
                sql.append(String.format(" OR  %s.dept_id=%d",dataScope.deptAlias(),user.getDeptId()));
            }else if (DATA_SCOPE_DEPT_AND_CHILD.equals(ds)){
                // 查看当前部门和子部门
                sql.append(String.format(" OR  %s.dept_id in (select dept_id from sys_dept where dept_id=%d or find_in_set(%d,ancestors) )",dataScope.deptAlias(),user.getDeptId(),user.getDeptId()));
            }else if (DATA_SCOPE_SELF.equals(ds)){
                // 只能查看自己的数据，自己个人信息或者业务的数据，不能查看部门数据
                String s = dataScope.userAlias();
                if (s==null){
                    // 永远不满足
                    sql.append(" OR 1=0");
                }else{
                    // 根据role#data_scope 查找对应的部门，dept_id 连接 user
                    sql.append(String.format(" OR %s.user_id=%d",s,user.getUserId()));
                }
            }

        }


        // 第一个 OR 改成 and (xxx  or  xxx or xxx)
        if (arg != null && arg instanceof BaseEntity){
            BaseEntity baseEntity = (BaseEntity) arg;
            baseEntity.getParams().put(DATA_SCOPE,"  AND ( "+sql.substring(4)+" )");
        }

    }
}
