package com.myweb.myshiro.mapper;

import com.myweb.myshiro.pojo.RolePermission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author
 * @create 2020/4/27-22:05
 **/
@Component
@Qualifier("template")
public interface RolePermissionMapper {
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    @Insert("insert into role_permission values(null,#{rid},#{pid})")
    public void addRP(RolePermission rolePermission);
}
