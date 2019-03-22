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
public class UserService {

    @Resource
    private AuthorityUserRepository authorityUserRepository;

    /**
     * 根据用户名查询密码
     */
    public String findPasswordByUsernameAfterValidTime(String username) {
        String nowTime = TimeUtil.FORMAT.get().format(System.currentTimeMillis());
        return authorityUserRepository.findPasswordByUsernameAfterValidTime(username, nowTime);
    }

    /**
     * 根据用户名获得角色名称
     */
    public List<String> findRoleNameByUsername(String username) {
        return authorityUserRepository.findRoleNameByUsername(username);
    }

    /**
     * 根据用户名获得菜单信息
     */
    public Map<String, Object> findMenuInfoByUsername(String username) {
        String nickname = authorityUserRepository.findNickNameByUsername(username);
        Map<String, Object> userInfo = new HashMap<>();
        List<Map<String, Object>> menuInfoList = new ArrayList<>();
        // 判断是否最高权限
        List<String> rootUrlByUsername = authorityUserRepository.findUrlsByUsername(username);
        boolean isHighestAuthority = rootUrlByUsername.contains("/**");
        if (isHighestAuthority) {
            List<Map<String, Object>> rootMenuInfoList = authorityUserRepository.findRootMenuInfo();
            rootMenuInfoList.forEach(rootMenuInfo -> {
                int id = (int) rootMenuInfo.get("id");
                List<Map<String, Object>> children = authorityUserRepository.findMenuInfoByParentId(id);
                Map<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("url", rootMenuInfo.get("url"));
                map.put("menuName", rootMenuInfo.get("menuName"));
                map.put("parentId", rootMenuInfo.get("parentId"));
                map.put("urlPre", rootMenuInfo.get("urlPre"));
                map.put("children", children);
                menuInfoList.add(map);
            });
        } else {
            List<Map<String, Object>> rootMenuInfoByUsername = authorityUserRepository.findRootMenuInfoByUsername(username);
            if (!ObjectUtils.isEmpty(rootMenuInfoByUsername)) {
                rootMenuInfoByUsername.forEach(rootMenuInfo -> {
                    int id = (int) rootMenuInfo.get("id");
                    List<Map<String, Object>> children = authorityUserRepository.findMenuInfoByParentId(id);
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("url", rootMenuInfo.get("url"));
                    map.put("menuName", rootMenuInfo.get("menuName"));
                    map.put("parentId", rootMenuInfo.get("parentId"));
                    map.put("urlPre", rootMenuInfo.get("urlPre"));
                    map.put("children", children);
                    menuInfoList.add(map);
                });
            }
            List<Integer> rootMenuIdOfPartialPermission = authorityUserRepository.findRootMenuIdOfPartialPermission(username);
            if (!ObjectUtils.isEmpty(rootMenuIdOfPartialPermission)) {
                rootMenuIdOfPartialPermission.forEach(menuId -> {
                    Map<String, Object> rootMenuInfo = authorityUserRepository.findMenuInfoByMenuId(menuId);
                    List<Map<String, Object>> children = authorityUserRepository.findChildrenMenuInfoByUsernameAndParentId(username, menuId);
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", menuId);
                    map.put("url", rootMenuInfo.get("url"));
                    map.put("menuName", rootMenuInfo.get("menuName"));
                    map.put("parentId", rootMenuInfo.get("parentId"));
                    map.put("urlPre", rootMenuInfo.get("urlPre"));
                    map.put("children", children);
                    menuInfoList.add(map);
                });
            }
        }
        userInfo.put("nickname", nickname);
        userInfo.put("username", username);
        userInfo.put("menuList", menuInfoList);
        return userInfo;
    }

    /**
     * 获得所有的菜单url
     */
    public List<String> findAllMenuUrl() {
        return authorityUserRepository.findAllMenuUrl();
    }

    /**
     * 根据菜单url获得需要拥有的角色
     */
    public String findRoleNameByMenuUrl(String url) {
        return authorityUserRepository.findRoleNameByMenuUrl(url);
    }

    /**
     * 查询用户名是否已存在 存在:返回true
     */
    public boolean usernameIsExistence(String username) {
        int count = authorityUserRepository.findCountByUsername(username);
        return count == 1;
    }

    /**
     * 新增用户信息
     */
    public void addUserInfo(String nickname, String username, String password, String email, String phone, String validTime, String remark) {
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        if (ObjectUtils.isEmpty(validTime)) {
            String validTimeDefault = TimeUtil.FORMAT.get().format(timeId + 7 * 24 * 60 * 60 * 1000);
            authorityUserRepository.addUserInfo(nickname, username, password, email, phone, validTimeDefault, nowTime, remark);
        }
        authorityUserRepository.addUserInfo(nickname, username, password, email, phone, validTime, nowTime, remark);
    }

    /**
     * 为用户分配角色(批量角色id:逗号分隔)
     */
    public void addRolesForUser(String userId, String roleIds) {
        int uId = Integer.parseInt(userId);
        authorityUserRepository.deleteRolesByUserId(uId);
        String nowTime = TimeUtil.FORMAT.get().format(System.currentTimeMillis());
        String[] roleIdsArray = roleIds.split(",");
        for (String roleId : roleIdsArray) {
            int rId = Integer.parseInt(roleId);
            authorityUserRepository.addRoleForUser(uId, rId, nowTime);
        }
    }

    /**
     * 删除用户信息 (批量删除id 逗号分隔)
     */
    public void deleteUserInfo(String id) {
        String[] ids = id.split(",");
        for (String userId : ids) {
            int uId = Integer.parseInt(userId);
            authorityUserRepository.deleteRolesByUserId(uId);
            authorityUserRepository.deleteUserInfoByUserId(uId);
        }
    }

    /**
     * 修改用户信息 (批量角色id:逗号分隔)
     */
    public void updateUserInfo(String id, String nickname, String username, String password, String email, String phone, String validTime, String remark, String roleIds) {
        int userId = Integer.parseInt(id);
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        if (ObjectUtils.isEmpty(validTime)) {
            String validTimeDefault = TimeUtil.FORMAT.get().format(timeId + 7 * 24 * 60 * 60 * 1000);
            authorityUserRepository.updateUserInfoByUserId(userId, nickname, username, password, email, phone, validTimeDefault, nowTime, remark);
        }
        authorityUserRepository.updateUserInfoByUserId(userId, nickname, username, password, email, phone, validTime, nowTime, remark);
        authorityUserRepository.deleteRolesByUserId(userId);
        String[] roleIdsArray = roleIds.split(",");
        for (String roleId : roleIdsArray) {
            int rId = Integer.parseInt(roleId);
            authorityUserRepository.addRoleForUser(userId, rId, nowTime);
        }
    }

    /**
     * 获取所有的用户信息
     */
    public List<Map<String, Object>> findAllUserInfo(String pageNum, String pageSize, String username, String nickname) {
        int rowNum = Integer.parseInt(pageNum);
        int size = Integer.parseInt(pageSize);
        int row = (rowNum - 1) * size;
        String uName = "%" + username + "%";
        String nName = "%" + nickname + "%";
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> allUserInfo = authorityUserRepository.findAllUserInfo(row, size, uName, nName);
        for (Map<String, Object> userInfo : allUserInfo) {
            int userId = (int) userInfo.get("id");
            List<Map<String, Object>> roleInfo = authorityUserRepository.findRoleInfoByUserId(userId);
            Map<String, Object> map = new HashMap<>();
            map.put("id", userId);
            map.put("nickname", userInfo.get("nickname"));
            map.put("username", userInfo.get("username"));
            map.put("password", userInfo.get("password"));
            map.put("email", userInfo.get("email"));
            map.put("phone", userInfo.get("phone"));
            map.put("validTime", userInfo.get("validTime"));
            map.put("remark", userInfo.get("remark"));
            map.put("roleList", roleInfo);
            list.add(map);
        }
        return list;
    }

    /**
     * 修改的用户名是否存在 不存在 返回true
     */
    public boolean isNotExistenceOfUpdateUsername(String id, String username) {
        int userId = Integer.parseInt(id);
        int count = authorityUserRepository.isNotExistenceOfUpdateUsername(userId, username);
        return count == 0;
    }
}
