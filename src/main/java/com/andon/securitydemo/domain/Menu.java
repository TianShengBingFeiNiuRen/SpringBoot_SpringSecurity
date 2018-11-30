package com.andon.securitydemo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //id

    @Column(name = "url")
    private String url; //请求路径

    @Column(name = "menu_name")
    private String menuName; //菜单名称

    @Column(name = "parent_id")
    private Long parentId; //父菜单id

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private List<Menu> children; //菜单子集

    @Column(name = "remark")
    private String remark; //备注

    public Menu() {
    }

    public Menu(String url, String menuName, Long parentId, List<Menu> children, String remark) {
        this.url = url;
        this.menuName = menuName;
        this.parentId = parentId;
        this.children = children;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", menuName='" + menuName + '\'' +
                ", parentId=" + parentId +
                ", children=" + children +
                ", remark='" + remark + '\'' +
                '}';
    }
}
