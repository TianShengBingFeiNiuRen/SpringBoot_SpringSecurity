package com.andon.securitydemo.domain;

import java.io.Serializable;

public class MenuRolePK implements Serializable {

    private Long menuId;

    private Long roleId;

    public MenuRolePK() {
    }

    public MenuRolePK(Long menuId, Long roleId) {
        this.menuId = menuId;
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "MenuRolePK{" +
                "menuId=" + menuId +
                ", roleId=" + roleId +
                '}';
    }
}
