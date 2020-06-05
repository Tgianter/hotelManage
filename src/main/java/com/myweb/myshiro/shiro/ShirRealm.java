package com.myweb.myshiro.shiro;

import com.myweb.myshiro.pojo.Permission;
import com.myweb.myshiro.pojo.Role;
import com.myweb.myshiro.pojo.User;
import com.myweb.myshiro.service.Impl.PermissionServiceImpl;
import com.myweb.myshiro.service.Impl.RoleServiceImpl;
import com.myweb.myshiro.service.Impl.UserServiceImpl;
import com.myweb.myshiro.utils.TransferToSetString;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author
 * @create 2020/4/26-18:12
 **/

public class ShirRealm extends AuthorizingRealm {
    @Autowired
    PermissionServiceImpl permissionService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleServiceImpl roleService;
    @Autowired
    TransferToSetString transfer;


    //当登录用户认证成功后，shiro的SecurityManager会自动调用这个授权方法，
    //以获得用户的Role(角色)和Permission(权限)
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取 认证(Authentication)中传给securityManager的数据
        //此处的获得的user即为认证中回传simpleAuthenticationInfo
        User user= (User) principalCollection.getPrimaryPrincipal();
//        String username= (String) principalCollection.getPrimaryPrincipal();

        List<Permission> permissions=userService.getPermission(user.getName());
//        List<Permission> permissions=userService.getPermission(username);
        Set<String> permissionsString=transfer.transferPermission(permissions);

        List<Role> roles=userService.getRole(user.getName());
//        List<Role> roles=userService.getRole(username);
        Set<String> rolesString=transfer.transferRole(roles);

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //setRoles的参数只能是Set<String>类型
        info.setRoles(rolesString);
        //setStringPermissions的参数也只能是Set<String>
        info.setStringPermissions(permissionsString);
        return info;
    }

    //在登录页面使用了Subject.log(UsernamePasswordToken),Shiro的SecurityManager会自动的调用Realm的这个
    //方法，以认证登录用户是否存在，当
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String username=token.getPrincipal().toString();
        User user=userService.getUser(username);
        if(null==user){
            return null;
        }
        //获取存储在数据库中的加密后的密码
        String passwordInDB=user.getPassword();
        String salt=user.getSalt();
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(
//                username,
                //当使用自定的cacheManager整合redis时，传给securityManager的值必须为user，
                //因为redis缓存pojo实体时，是用id作为数据的键，而只有返回user时，securityManager才能获得id
                //并把id传给cacheManager进行redis缓存
                user,
                passwordInDB,
                //将存储在DB中的salt转换为ByteSource类型，并作为认证信息回传
                //这样HashCredentialsMatcher(解码器)才能正确使用salt解码
                ByteSource.Util.bytes(salt),
                getName());
        return info;
    }
}
