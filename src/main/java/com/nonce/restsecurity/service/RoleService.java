package com.nonce.restsecurity.service;

import com.nonce.restsecurity.dao.AuthorityUserRepository;
import com.nonce.restsecurity.util.SecurityResponse;
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
public class RoleService {

    @Resource
    private AuthorityUserRepository authorityUserRepository;

    /**
     * 查询角色是否已存在 存在:返回true
     */
    public boolean roleNameIsExistence(String roleName) {
        int count = authorityUserRepository.roleNameIsExistence(roleName);
        return count == 1;
    }

    /**
     * 新增用户信息
     */
    public void addRoleInfo(String roleName, String roleNameCN, String remark) {
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        authorityUserRepository.addRoleInfo(roleName, roleNameCN, nowTime, remark);
    }

    /**
     * 为角色分配可访问的资源菜单 (批量菜单id:逗号分隔)
     */
    public void addMenusForRole(String roleId, String menuIds) {
        int rId = Integer.parseInt(roleId);
        authorityUserRepository.deleteMenusByRoleId(rId);
        String nowTime = TimeUtil.FORMAT.get().format(System.currentTimeMillis());
        String[] menuIdsArray = menuIds.split(",");
        for (String menuId : menuIdsArray) {
            int mId = Integer.parseInt(menuId);
            authorityUserRepository.addMenuForRole(rId, mId, nowTime);
        }
    }

    /**
     * 删除角色信息 (批量删除id 逗号分隔)
     */
    public void deleteRoleInfo(String id) {
        String[] ids = id.split(",");
        for (String roleId : ids) {
            int rId = Integer.parseInt(roleId);
            authorityUserRepository.deleteMenusByRoleId(rId);
            authorityUserRepository.deleteRoleInfoByRoleId(rId);
        }
    }

    /**
     * 修改角色信息 (批量菜单id:逗号分隔)
     */
    public void updateRoleInfo(String id, String roleName, String roleNameCN, String remark) {
        int roleId = Integer.parseInt(id);
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        authorityUserRepository.updateRoleInfoByRoleId(roleId, roleName, roleNameCN, nowTime, remark);
    }

    /**
     * 获取所有的角色信息 (分页加模糊查询)
     */
    public List<Map<String, Object>> findAllRoleInfo(String pageNum, String pageSize, String roleNameCN, SecurityResponse securityResponse) {
        int rowNum = Integer.parseInt(pageNum);
        int size = Integer.parseInt(pageSize);
        int row = (rowNum - 1) * size;
        String name = "%" + roleNameCN + "%";
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> allRoleInfo = authorityUserRepository.findAllRoleInfo(row, size, name);
        for (Map<String, Object> roleInfo : allRoleInfo) {
            int roleId = (int) roleInfo.get("id");

            List<Map<String, Object>> menuInfoList = new ArrayList<>();
            List<Map<String, Object>> rootMenuInfoByRoleId = authorityUserRepository.findRootMenuInfoByRoleId(roleId);
            rootMenuInfoByRoleId.forEach(rootMenuInfo -> {
                int id = (int) rootMenuInfo.get("id");
                if (id == 0) {
                    List<Map<String, Object>> rootMenuInfoList = authorityUserRepository.findRootMenuInfo();
                    rootMenuInfoList.forEach(rootMenu -> {
                        int rootId = (int) rootMenu.get("id");
                        List<Map<String, Object>> children = authorityUserRepository.findMenuInfoByParentId(rootId);
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", rootId);
                        map.put("url", rootMenu.get("url"));
                        map.put("menuName", rootMenu.get("menuName"));
                        map.put("parentId", rootMenu.get("parentId"));
                        map.put("remark", rootMenu.get("remark"));
                        map.put("urlPre", rootMenu.get("urlPre"));
                        map.put("children", children);
                        menuInfoList.add(map);
                    });
                } else {
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
                }
            });
            List<Integer> rootMenuIdOfPartialPermissionByRoleId = authorityUserRepository.findRootMenuIdOfPartialPermissionByRoleId(roleId);
            if (!ObjectUtils.isEmpty(rootMenuIdOfPartialPermissionByRoleId)) {
                rootMenuIdOfPartialPermissionByRoleId.forEach(menuId -> {
                    Map<String, Object> rootMenuInfo = authorityUserRepository.findMenuInfoByMenuId(menuId);
                    List<Map<String, Object>> children = authorityUserRepository.findChildrenMenuInfoByRoleIdAndParentId(roleId, menuId);
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", menuId);
                    map.put("url", rootMenuInfo.get("url"));
                    map.put("menuName", rootMenuInfo.get("menuName"));
                    map.put("parentId", rootMenuInfo.get("parentId"));
                    map.put("remark", rootMenuInfo.get("remark"));
                    map.put("urlPre", rootMenuInfo.get("urlPre"));
                    map.put("children", children);
                    menuInfoList.add(map);
                });
            }
            Map<String, Object> map = new HashMap<>();
            map.put("id", roleId);
            map.put("roleName", roleInfo.get("roleName"));
            map.put("roleNameCN", roleInfo.get("roleNameCN"));
            map.put("remark", roleInfo.get("remark"));
            map.put("menuList", menuInfoList);
            list.add(map);
        }
        int roleInfoSize = authorityUserRepository.findAllRoleInfoSize(name);
        securityResponse.setSuccess(true);
        securityResponse.setCode("1");
        securityResponse.setMessage("Find all role success!!");
        securityResponse.setData(list);
        securityResponse.setTotal(roleInfoSize);
        return list;
    }

    /**
     * 修改的角色名是否存在 不存在 返回true
     */
    public boolean isNotExistenceOfUpdateRoleName(String id, String roleName) {
        int roleId = Integer.parseInt(id);
        int count = authorityUserRepository.isNotExistenceOfUpdateRoleName(roleId, roleName);
        return count == 0;
    }
}
