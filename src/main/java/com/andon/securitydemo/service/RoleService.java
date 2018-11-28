package com.andon.securitydemo.service;

import com.andon.securitydemo.dao.MenuRoleRepository;
import com.andon.securitydemo.dao.RoleRepository;
import com.andon.securitydemo.dao.UserRoleRepository;
import com.andon.securitydemo.domain.MenuRole;
import com.andon.securitydemo.domain.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public List<Role> findByRoleNameOrRoleNameCN(String roleName, String roleNameCN){
        return roleRepository.findByRoleNameOrRoleNameCN(roleName, roleNameCN);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Resource
    private UserRoleRepository userRoleRepository;

    public void delete(Long roleId) {
        Integer integer = userRoleRepository.deleteByRoleId(roleId);
        System.out.println(integer);
        roleRepository.deleteById(roleId);
    }

    @Resource
    private MenuRoleRepository menuRoleRepository;

    /**
     * 为角色添加可访问的资源菜单
     */
    public void addMenus(Long roleId, Long[] menuIds) {
        Integer integer = menuRoleRepository.deleteByRoleId(roleId);
        System.out.println(integer);
        for (Long menuId : menuIds) {
            MenuRole menuRole = new MenuRole(menuId, roleId);
            menuRoleRepository.save(menuRole);
        }
    }
}
