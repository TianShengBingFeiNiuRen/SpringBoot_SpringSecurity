package com.andon.securitydemo.service;

import com.andon.securitydemo.dao.UserRepository;
import com.andon.securitydemo.dao.UserRoleRepository;
import com.andon.securitydemo.domain.Menu;
import com.andon.securitydemo.domain.Role;
import com.andon.securitydemo.domain.User;
import com.andon.securitydemo.domain.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class UserService {

    @Resource
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 获取用户可以访问的资源菜单
     */
    public Set<Menu> getMenusByUsername(String username) {
        TreeSet<Menu> menus = new TreeSet<>((o1, o2) -> (int) (o1.getId() - o2.getId()));
        User user = findByUsername(username);
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            menus.addAll(role.getMenus());
        }
        return menus;
    }

    @Resource
    private UserRoleRepository userRoleRepository;

    /**
     * 为用户添加角色
     */
    public void addRoles(Long userId, Long[] roleIds) {
        Integer integer = userRoleRepository.deleteByUserId(userId);
        System.out.println(integer);
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole(userId, roleId);
            userRoleRepository.save(userRole);
        }
    }

}
