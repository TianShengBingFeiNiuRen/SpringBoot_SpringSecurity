package com.nonce.restsecurity.service;

import com.nonce.restsecurity.dao.AuthorityUserRepository;
import com.nonce.restsecurity.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * @date 2019/3/20
 */
@SuppressWarnings("Duplicates")
@Service
@Transactional
public class MenuService {

    @Resource
    private AuthorityUserRepository authorityUserRepository;

    /**
     * 查询菜单是否已存在 存在:返回true
     */
    public boolean menuNameIsExistence(String menuName, String parentId) {
        int count = authorityUserRepository.menuNameIsExistence(menuName, parentId);
        return count == 1;
    }

    /**
     * 新增菜单信息
     */
    public Map<String, String> addMenuInfo(String url, String menuName, String parentId, String remark, String urlPre) {
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        authorityUserRepository.addMenuInfo(url, menuName, parentId, nowTime, remark, urlPre);
        return authorityUserRepository.findMenuInfoByMenuName(menuName, parentId);
    }

    /**
     * 删除菜单信息 (批量删除id 逗号分隔)
     */
    public void deleteMenuInfo(String id) {
        String[] ids = id.split(",");
        for (String menuId : ids) {
            int mId = Integer.parseInt(menuId);
            authorityUserRepository.deleteRolesByMenuId(mId);
            authorityUserRepository.deleteMenuInfoByMenuId(mId);
            List<Integer> childrenMenuIds = authorityUserRepository.selectChildrenMenuIds(mId);
            if (!ObjectUtils.isEmpty(childrenMenuIds)) {
                childrenMenuIds.forEach(childId -> {
                    authorityUserRepository.deleteRolesByMenuId(childId);
                    authorityUserRepository.deleteMenuInfoByMenuId(childId);
                });
            }
        }
    }

    /**
     * 修改角色信息
     */
    public void updateMenuInfo(String id, String url, String menuName, String parentId, String remark, String urlPre) {
        int menuId = Integer.parseInt(id);
        String nowTime = TimeUtil.FORMAT.get().format(System.currentTimeMillis());
        authorityUserRepository.updateMenuInfoByMenuId(menuId, url, menuName, parentId, nowTime, remark, urlPre);
    }

    /**
     * 获取所有菜单信息
     */
    public List<Map<String, Object>> findAllMenuInfo() {
        List<Map<String, Object>> menuInfoList = new ArrayList<>();
        List<Map<String, Object>> rootMenuInfoList = authorityUserRepository.findRootMenuInfo();
        rootMenuInfoList.forEach(rootMenuInfo -> {
            int id = (int) rootMenuInfo.get("id");
            List<Map<String, Object>> children = authorityUserRepository.findMenuInfoByParentId(id);
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("url", rootMenuInfo.get("url"));
            map.put("menuName", rootMenuInfo.get("menuName"));
            map.put("parentId", rootMenuInfo.get("parentId"));
            map.put("remark", rootMenuInfo.get("remark"));
            map.put("urlPre", rootMenuInfo.get("urlPre"));
            map.put("children", children);
            menuInfoList.add(map);
        });
        return menuInfoList;
    }

    /**
     * 修改的菜单名是否存在 不存在 返回true
     */
    public boolean isNotExistenceOfUpdateMenuName(String id, String menuName, String parentId) {
        int menuId = Integer.parseInt(id);
        int count = authorityUserRepository.isNotExistenceOfUpdateMenuName(menuId, menuName, parentId);
        return count == 0;
    }
}
