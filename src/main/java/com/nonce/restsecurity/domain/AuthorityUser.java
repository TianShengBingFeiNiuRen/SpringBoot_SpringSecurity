package com.nonce.restsecurity.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andon
 * @date 2019/3/20
 */
@Entity
@Table(name = "authority_user")
public class AuthorityUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nickname")
    private String nickname; //昵称

    @Column(name = "username")
    private String username; //用户名

    @Column(name = "password")
    private String password; //密码

    @Column(name = "email")
    private String email; //邮箱

    @Column(name = "phone")
    private String phone; //手机号

    @Column(name = "valid_time")
    private String validTime; //有效截止时间

    @Column(name = "update_time")
    private String updateTime; //更新时间

    @Column(name = "remark")
    private String remark; //备注

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AuthorityUser{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", validTime='" + validTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}