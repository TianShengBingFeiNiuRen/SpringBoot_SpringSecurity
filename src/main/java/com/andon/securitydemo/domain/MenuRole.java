package com.andon.securitydemo.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "menu_role")
@IdClass(MenuRolePK.class)
public class MenuRole implements Serializable {

    @Id
    @Column(name = "menu_id")
    private Long menuId;

    @Id
    @Column(name = "role_id")
    private Long roleId;

    public MenuRole() {
    }

    public MenuRole(Long menuId, Long roleId) {
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
        return "MenuRole{" +
                "menuId=" + menuId +
                ", roleId=" + roleId +
                '}';
    }
}
