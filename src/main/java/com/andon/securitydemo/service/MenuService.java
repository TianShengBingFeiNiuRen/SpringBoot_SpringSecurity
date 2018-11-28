package com.andon.securitydemo.service;

import com.andon.securitydemo.dao.MenuRepository;
import com.andon.securitydemo.dao.MenuRoleRepository;
import com.andon.securitydemo.domain.Menu;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MenuService {

    @Resource
    private MenuRepository menuRepository;

    public List<Menu> findAll() {
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

}
