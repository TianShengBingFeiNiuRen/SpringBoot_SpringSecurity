package com.andon.securitydemo.util;

import java.io.Serializable;
import java.util.Arrays;

public class MenuRoles implements Serializable {

    private Long roleId; //角色Id
    private Long[] menuIds; //资源菜单Id数组

    public MenuRoles() {
    }

    public MenuRoles(Long roleId, Long[] menuIds) {
        this.roleId = roleId;
        this.menuIds = menuIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(Long[] menuIds) {
        this.menuIds = menuIds;
    }

    @Override
    public String toString() {
        return "MenuRoles{" +
                "roleId=" + roleId +
                ", menuIds=" + Arrays.toString(menuIds) +
                '}';
    }
}
