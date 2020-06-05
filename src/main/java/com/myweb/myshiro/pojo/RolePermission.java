package com.myweb.myshiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @create 2020/4/26-13:27
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolePermission {
    private int id;
    private int rid;
    private int pid;
}
