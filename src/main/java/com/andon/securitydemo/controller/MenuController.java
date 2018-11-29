package com.andon.securitydemo.controller;

import com.andon.securitydemo.domain.Menu;
import com.andon.securitydemo.service.MenuService;
import com.andon.securitydemo.util.SecurityResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单管理
 */
@RestController
@RequestMapping(value = "/security")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 获取所有菜单信息
     */
    @PostMapping(value = "/menu/findAll")
    public SecurityResponse findAll() {
        List<Menu> menus = menuService.findMainMenu();
        return new SecurityResponse("1", "Find all menu success!!", menus);
    }

    /**
     * 新增菜单
     */
    @PostMapping(value = "/menu/add")
    public SecurityResponse add(Menu menu) {
        Menu menuExit = menuService.findByMenuName(menu.getMenuName());
        if (ObjectUtils.isEmpty(menuExit)) {
            Menu save = menuService.save(menu);
            return new SecurityResponse("1", "Add menu success!!", save);
        }
        return new SecurityResponse("-1", "Menu already exists!!", menuExit);
    }

    /**
     * 修改菜单信息
     */
    @PostMapping(value = "/menu/update")
    public SecurityResponse update(@RequestBody Menu menu) {
        Menu save = menuService.save(menu);
        return new SecurityResponse("1", "Update menu success!!", save);
    }

    /**
     * 删除菜单
     */
    @PostMapping(value = "/menu/delete")
    public SecurityResponse delete(Long id) {
        menuService.delete(id);
        return new SecurityResponse("1", "Delete menu success!!", id);
    }

}
