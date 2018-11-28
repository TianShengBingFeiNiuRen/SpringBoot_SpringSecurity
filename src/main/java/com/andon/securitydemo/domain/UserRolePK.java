package com.andon.securitydemo.domain;

import java.io.Serializable;

public class UserRolePK implements Serializable {

    private Long userId;

    private Long roleId;

    public UserRolePK() {
    }

    public UserRolePK(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "UserRolePK{" +
                "userId=" + userId +
                ", roleId=" + roleId +
                '}';
    }
}
