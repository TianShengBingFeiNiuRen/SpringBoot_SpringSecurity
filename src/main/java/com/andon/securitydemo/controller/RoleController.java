package com.andon.securitydemo.controller;

import com.andon.securitydemo.domain.Role;
import com.andon.securitydemo.service.RoleService;
import com.andon.securitydemo.util.MenuRoles;
import com.andon.securitydemo.util.SecurityResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping(value = "/security")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 获取所有角色信息
     */
    @PostMapping(value = "/role/findAll")
    public SecurityResponse findAll() {
        List<Role> roles = roleService.findAll();
        return new SecurityResponse("1", "Find all role success!!", roles);
    }

    /**
     * 新增角色
     */
    @PostMapping(value = "/role/add")
    public SecurityResponse add(Role role) {
        List<Role> roleExit = roleService.findByRoleNameOrRoleNameCN(role.getRoleName(), role.getRoleNameCN());
        if (ObjectUtils.isEmpty(roleExit)) {
            Role save = roleService.save(role);
            return new SecurityResponse("1", "Add role success!!", save);
        }
        return new SecurityResponse("-1", "Role already exists!!", roleExit);
    }

    /**
     * 修改角色信息
     */
    @PostMapping(value = "/role/update")
    public SecurityResponse update(Role role) {
        Role save = roleService.save(role);
        return new SecurityResponse("1", "Update role success!!", save);
    }

    /**
     * 删除角色
     */
    @PostMapping(value = "/role/delete")
    public SecurityResponse delete(Long id) {
        roleService.delete(id);
        return new SecurityResponse("1", "Delete role success!!", id);
    }

    /**
     * 为角色添加可访问的资源菜单
     */
    @PostMapping(value = "/role/addMenus")
    public SecurityResponse addMenus(@RequestBody MenuRoles menuRoles) {
        System.out.println(menuRoles);
        roleService.addMenus(menuRoles.getRoleId(), menuRoles.getMenuIds());
        return new SecurityResponse("1", "Role add menu success!!", menuRoles);
    }

}
