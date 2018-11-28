package com.andon.securitydemo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_name_CN")
    private String roleNameCN;

    @Column(name = "remark")
    private String remark;

    /*@ManyToMany(targetEntity = User.class, mappedBy = "roles")
    private List<User> users;*/

    @ManyToMany(targetEntity = Menu.class, fetch = FetchType.EAGER)
    @JoinTable(name = "menu_role", joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menus;

    public Role() {
    }

    public Role(String roleName, String roleNameCN, String remark, List<Menu> menus) {
        this.roleName = roleName;
        this.roleNameCN = roleNameCN;
        this.remark = remark;
        this.menus = menus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNameCN() {
        return roleNameCN;
    }

    public void setRoleNameCN(String roleNameCN) {
        this.roleNameCN = roleNameCN;
    }

    public String getRemark() {
        return remark;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleNameCN='" + roleNameCN + '\'' +
                ", remark='" + remark + '\'' +
                ", menus=" + menus +
                '}';
    }
}
