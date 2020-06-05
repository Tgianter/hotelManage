package com.myweb.myshiro.mapper;

import com.myweb.myshiro.pojo.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/4/27-21:59
 **/
@Component
@Qualifier("template")
public interface UserRoleMapper {
    @Options(useGeneratedKeys=true,keyColumn = "id",keyProperty = "id")
    @Insert("insert into user_role values(null,#{uid},#{rid})")
    public void addUR(UserRole userRole);
}
