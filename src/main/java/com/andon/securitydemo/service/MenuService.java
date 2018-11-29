package com.andon.securitydemo.service;

import com.andon.securitydemo.dao.MenuRepository;
import com.andon.securitydemo.dao.MenuRoleRepository;
import com.andon.securitydemo.dao.RoleRepository;
import com.andon.securitydemo.domain.Menu;
import com.andon.securitydemo.domain.MenuRole;
import com.andon.securitydemo.domain.Role;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class MenuService {

    @Resource
    private MenuRepository menuRepository;

    public List<Menu> findMainMenu() {
        return menuRepository.findByParentId(0L);
    }

    public Menu findByMenuName(String menuName) {
        return menuRepository.findByMenuName(menuName);
    }

    public Menu save(Menu menu) {
        List<Menu> children = menu.getChildren();
        if (!ObjectUtils.isEmpty(children)) {
            for (Menu child : children) {
                save(child);
            }
        }
        return menuRepository.save(menu);
    }

    @Resource
    private MenuRoleRepository menuRoleRepository;

    public void delete(Long menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        Menu menu = menuOptional.get();
        List<Menu> children = menu.getChildren();
        if (!ObjectUtils.isEmpty(children)) {
            for (Menu child : children) {
                delete(child.getId());
            }
        }
        Integer integer = menuRoleRepository.deleteByMenuId(menuId);
        System.out.println(integer);
        menuRepository.deleteById(menuId);
    }

    public List<Menu> findAllMenu(){
        return menuRepository.findAll();
    }

    @Resource
    private RoleRepository roleRepository;

    /**
     * 获取访问该菜单需要的角色
     */
    public Set<String> findRolesByUrl(String url) {
        Set<String> roleNameSet = new HashSet<>();
        List<Menu> menus = menuRepository.findByUrl(url);
        for (Menu menu : menus) {
            List<MenuRole> menuRoles = menuRoleRepository.findByMenuId(menu.getId());
            for (MenuRole menuRole : menuRoles) {
                Long roleId = menuRole.getRoleId();
                Optional<Role> roleOptional = roleRepository.findById(roleId);
                String roleName = roleOptional.get().getRoleName();
                roleNameSet.add(roleName);
            }
        }
        return roleNameSet;
    }

}
