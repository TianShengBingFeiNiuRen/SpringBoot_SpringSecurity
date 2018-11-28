package com.andon.securitydemo.util;

import java.io.Serializable;
import java.util.Arrays;

public class UserRoles implements Serializable {

    private Long userId; //用户Id
    private Long[] roleIds; //角色Id数组

    public UserRoles() {
    }

    public UserRoles(Long userId, Long[] roleIds) {
        this.userId = userId;
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserRoles{" +
                "userId=" + userId +
                ", roleIds=" + Arrays.toString(roleIds) +
                '}';
    }
}
