package com.myweb.myshiro.mapper;

import com.myweb.myshiro.pojo.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-14:28
 **/
@Component
@Qualifier("template")

public interface RoleMapper {
    /**
     * @param =uid,
     * 此时传入的参数为userId
     */
    @Select("select * from role where id in(select rid from user_role where uid =#{uid})")
    public List<Role> getAllRoleById(int uid);

    @Select("select * from role where name=#{name}")
    @Results({
            @Result(property = "id",column = "id"),

            @Result(property = "users",column = "id",
                many = @Many(select = "com.myweb.myshiro.mapper.UserMapper.getAllUserByRid")
            ) ,
            @Result(property = "permissions",column = "id",
                    many=@Many(select = "com.myweb.myshiro.mapper.PermissionMapper.getAllPermissionByRid"))
    })
    public Role getRoleByName(String name);

    @Select("select * from role")
    public List<Role> listRole();
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("insert into role values(null,#{name})")
    public void addRole(Role role);

    @Delete("delete from role where id=#{id}")
    public void deleteRole(int id);
}
