package com.myweb.myshiro.mapper;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author
 * @create 2020/4/26-14:19
 **/
@Component
@Qualifier("template")
public interface UserMapper {

    @Select("select * from user where name=#{name}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "roles",column = "id",
                    many = @Many(select = "com.myweb.myshiro.mapper.RoleMapper.getAllRoleById"

            )),
            @Result(property = "permissions",column = "id",
                many = @Many(select = "com.myweb.myshiro.mapper.UserMapper.getAllPermissionById"
                )
            )
    })
    public User getUserByName(String name);

    @Select("select * from permission where id in(" +
            "select pid from role_permission where rid in(" +
            "select rid from user_role where uid=#{uid}))")
    public List<Permission> getAllPermissionById(int uid);

    //当有多条sql语句时，数据库驱动Driver必须设置allowMultiQueries=true以开启支持一次执行多条sql语句
    //注意是在配置文件中设置
    //Options实现了把自增长id返回给参数中user的id
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("insert into user values(null,#{name},#{password},#{salt})")
//    @Delete("delete from user where name=#{name}")
//    @Select("select id from user where name=#{name}")
    public void addUser(User user);

    @Select("select * from user where id in(select uid from user_role where rid=#{rid})")
    public List<User> getAllUserByRid(int rid);

    @Select("select * from user")
    public List<User> listUser();

    @Delete("delete from user where id=#{id}")
    public void deleteRole(int id);
}
