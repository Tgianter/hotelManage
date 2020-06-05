package com.myweb.myshiro.mapper;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-22:42
 **/
//指定了此DAO使用的SQLSessionTemplate，实现和对应的Mybatis配置的数据源匹配
//如果使用多数据源，只能使用@Qualifier()来指定数据源，不能使用@Mapper
@Qualifier("template")
@Component
public interface PermissionMapper {
    @Select("select * from permission where name=#{name}")
    @Results({
            @Result(property = "roles",column= "id",
                    many = @Many(select = "com.myweb.myshiro.mapper.PermissionMapper.getAllRoleByPid")
            )
    })
    public Permission getPermissionByName(String name);

    @Select("select * role where id in(select rid from role_permission where rid=#{rid})")
    public List<Role> getAllRoleByPid(int rid);

    @Select("select * from permission where id in(select pid from role_permission where rid =#{rid})")
    public List<Permission> getAllPermissionByRid(int rid);

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into permission values(null,#{name})")
    public void addPermission(Permission permission);

    @Select("select * from permission")
    public List<Permission> listPermission();
}
