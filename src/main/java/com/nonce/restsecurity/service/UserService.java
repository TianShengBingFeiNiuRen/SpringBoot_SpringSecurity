package com.nonce.restsecurity.service;

import com.nonce.restsecurity.config.UrlResponse;
import com.nonce.restsecurity.dao.AuthorityUserRepository;
import com.nonce.restsecurity.util.SecurityResponse;
import com.nonce.restsecurity.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

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
    public Map<String, Object> findMenuInfoByUsername(String username, UrlResponse response) {
        Map<String, Object> userIdAndNickNameByUsername = authorityUserRepository.findUserIdAndNickNameAndRemarkByUsername(username);
        int userId = (int) userIdAndNickNameByUsername.get("id");
        String nickname = (String) userIdAndNickNameByUsername.get("nickname");
        String remark = (String) userIdAndNickNameByUsername.get("remark");
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
                map.put("remark", rootMenuInfo.get("remark"));
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
                    map.put("remark", rootMenuInfo.get("remark"));
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
                    map.put("remark", rootMenuInfo.get("remark"));
                    map.put("urlPre", rootMenuInfo.get("urlPre"));
                    map.put("children", children);
                    menuInfoList.add(map);
                });
            }
        }
        userInfo.put("nickname", nickname);
        userInfo.put("userId", userId);
        userInfo.put("username", username);
        userInfo.put("remark", remark);
        userInfo.put("menuList", menuInfoList);
        if (!ObjectUtils.isEmpty(menuInfoList)) {
            response.setTotal(menuInfoList.size());
        }
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
    public List<String> findRoleNameByMenuUrl(String url) {
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
        String encode = new BCryptPasswordEncoder().encode(password);
        long timeId = System.currentTimeMillis();
        String nowTime = TimeUtil.FORMAT.get().format(timeId);
        if (ObjectUtils.isEmpty(validTime)) {
            String validTimeDefault = TimeUtil.FORMAT.get().format(timeId + 7 * 24 * 60 * 60 * 1000);
            authorityUserRepository.addUserInfo(nickname, username, encode, email, phone, validTimeDefault, nowTime, remark);
        } else {
            authorityUserRepository.addUserInfo(nickname, username, encode, email, phone, validTime, nowTime, remark);
        }
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
     * 修改用户信息
     */
    public void updateUserInfo(String id, String nickname, String username, String password, String email, String phone, String validTime, String remark) {
        int userId = Integer.parseInt(id);
        String nowTime = TimeUtil.FORMAT.get().format(System.currentTimeMillis());
        if (ObjectUtils.isEmpty(validTime)) {
            if (ObjectUtils.isEmpty(password)) {
                authorityUserRepository.updateUserInfoByUserIdExcludeValidTimeAndPassword(userId, nickname, username, email, phone, nowTime, remark);
            } else {
                String encode = new BCryptPasswordEncoder().encode(password);
                authorityUserRepository.updateUserInfoByUserIdExcludeValidTime(userId, nickname, username, encode, email, phone, nowTime, remark);
            }
        } else {
            authorityUserRepository.updateUserInfoByUserIdExcludePassword(userId, nickname, username, email, phone, validTime, nowTime, remark);
        }

        if (ObjectUtils.isEmpty(validTime) && !ObjectUtils.isEmpty(password)) {
            String encode = new BCryptPasswordEncoder().encode(password);
            authorityUserRepository.updateUserInfoByUserIdExcludeValidTime(userId, nickname, username, encode, email, phone, nowTime, remark);
        } else if (!ObjectUtils.isEmpty(validTime) && ObjectUtils.isEmpty(password)) {
            authorityUserRepository.updateUserInfoByUserIdExcludePassword(userId, nickname, username, email, phone, validTime, nowTime, remark);
        }
    }

    /**
     * 获取所有的用户信息
     */
    public void findAllUserInfo(String pageNum, String pageSize, String username, String nickname, SecurityResponse securityResponse) {
        int rowNum = Integer.parseInt(pageNum);
        int size = Integer.parseInt(pageSize);
        int row = (rowNum - 1) * size;
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> allUserInfo = authorityUserRepository.findAllUserInfo(row, size, username, nickname);
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
        int userInfoSize = authorityUserRepository.findAllUserInfoSize(username, nickname);
        securityResponse.setSuccess(true);
        securityResponse.setCode("1");
        securityResponse.setMessage("Find all user success!!");
        securityResponse.setData(list);
        securityResponse.setTotal(userInfoSize);
    }

    /**
     * 修改的用户名是否存在 不存在 返回true
     */
    public boolean isNotExistenceOfUpdateUsername(String id, String username) {
        int userId = Integer.parseInt(id);
        int count = authorityUserRepository.isNotExistenceOfUpdateUsername(userId, username);
        return count == 0;
    }

    /**
     * 获取个人信息
     */
    public SecurityResponse personalUserInfoGet(String userId, String username) {
        SecurityResponse securityResponse = new SecurityResponse(false, "-1", "failure!!", "failure!!");
        try {
            if (!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(username)) {
                Map<String, String> userInfoByUserIdAndUsername = authorityUserRepository.getUserInfoByUserIdAndUsername(userId, username);
                securityResponse.setSuccess(true);
                securityResponse.setCode("1");
                securityResponse.setMessage("personalUserInfoGet success!!");
                securityResponse.setData(userInfoByUserIdAndUsername);
                securityResponse.setTotal(1);
            } else {
                securityResponse.setMessage("Incomplete information!!");
                securityResponse.setData("Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            securityResponse.setMessage("personalUserInfoGet failure!!");
            securityResponse.setData("personalUserInfoGet failure!!");
        }
        return securityResponse;
    }

    /**
     * 修改个人基本信息
     */
    public SecurityResponse personalUserInfoUpdate(String nickname, String username, String email, String phone, String password) {
        SecurityResponse securityResponse = new SecurityResponse(false, "-1", "failure!!", "failure!!");
        try {
            if (!ObjectUtils.isEmpty(username) && !ObjectUtils.isEmpty(password)) {
                // 密码校验
                boolean checkPassword = checkPassword(username, password);
                // 修改个人基本信息
                if (checkPassword) {
                    long timeId = System.currentTimeMillis();
                    String nowTime = TimeUtil.FORMAT.get().format(timeId);
                    int count = authorityUserRepository.updateUserBasicInfo(nickname, username, email, phone, nowTime);
                    securityResponse.setSuccess(true);
                    securityResponse.setCode("1");
                    securityResponse.setMessage("updateUserBasicInfo success!!");
                    securityResponse.setData("updateUserBasicInfo success!!");
                    securityResponse.setTotal(count);
                } else {
                    securityResponse.setMessage("The password is wrong!!");
                    securityResponse.setData("The password is wrong!!");
                }
            } else {
                securityResponse.setMessage("Incomplete information!!");
                securityResponse.setData("Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            securityResponse.setMessage("updateUserBasicInfo failure!!");
            securityResponse.setData("updateUserBasicInfo failure!!");
        }
        return securityResponse;
    }

    /**
     * 密码校验
     */
    public boolean checkPassword(String username, String password) {
        String userPasswordByUsername = authorityUserRepository.getUserPasswordByUsername(username);
        if (!ObjectUtils.isEmpty(userPasswordByUsername)) {
            return new BCryptPasswordEncoder().matches(password, userPasswordByUsername);
        } else {
            return false;
        }
    }

    /**
     * 修改个人密码
     */
    public SecurityResponse personalPasswordUpdateByOldPasswordAndNewPassword(String username, String oldPassword, String newPassword) {
        SecurityResponse securityResponse = new SecurityResponse(false, "-1", "failure!!", "failure!!");
        try {
            if (!ObjectUtils.isEmpty(username) && !ObjectUtils.isEmpty(oldPassword) && !ObjectUtils.isEmpty(newPassword)) {
                // 密码校验
                boolean checkPassword = checkPassword(username, oldPassword);
                if (checkPassword) {
                    String encode = new BCryptPasswordEncoder().encode(newPassword);
                    long timeId = System.currentTimeMillis();
                    String nowTime = TimeUtil.FORMAT.get().format(timeId);
                    int count = authorityUserRepository.updateUserPasswordByUsername(username, encode, nowTime);
                    securityResponse.setSuccess(true);
                    securityResponse.setCode("1");
                    securityResponse.setMessage("personalPasswordUpdate success!!");
                    securityResponse.setData("personalPasswordUpdate success!!");
                    securityResponse.setTotal(count);
                } else {
                    securityResponse.setMessage("The original password is wrong!!");
                    securityResponse.setData("The original password is wrong!!");
                }
            } else {
                securityResponse.setMessage("Incomplete information!!");
                securityResponse.setData("Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            securityResponse.setMessage("personalPasswordUpdate failure!!");
            securityResponse.setData("personalPasswordUpdate failure!!");
        }
        return securityResponse;
    }

    /**
     * 修改用户信息(管理员)
     */
    public SecurityResponse updateUserInfo(String nickname, String username, String email, String phone, String validTime, String remark) {
        SecurityResponse securityResponse = new SecurityResponse(false, "-1", "failure!!", "failure!!");
        try {
            if (!ObjectUtils.isEmpty(username)) {
                long timeId = System.currentTimeMillis();
                String nowTime = TimeUtil.FORMAT.get().format(timeId);
                int count = authorityUserRepository.updateUserInfo(nickname, username, email, phone, validTime, nowTime, remark);
                securityResponse.setSuccess(true);
                securityResponse.setCode("1");
                securityResponse.setMessage("updateUserInfo success!!");
                securityResponse.setData("updateUserInfo success!!");
                securityResponse.setTotal(count);
            } else {
                securityResponse.setMessage("Incomplete information!!");
                securityResponse.setData("Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            securityResponse.setMessage("updateUserInfo failure!!");
            securityResponse.setData("updateUserInfo failure!!");
        }
        return securityResponse;
    }

    /**
     * 修改用户密码(管理员)
     */
    public SecurityResponse updateUserPassword(String userId, String username, String password) {
        SecurityResponse securityResponse = new SecurityResponse(false, "-1", "failure!!", "failure!!");
        try {
            if (!ObjectUtils.isEmpty(userId) && !ObjectUtils.isEmpty(username) && !ObjectUtils.isEmpty(password)) {
                String encode = new BCryptPasswordEncoder().encode(password);
                long timeId = System.currentTimeMillis();
                String nowTime = TimeUtil.FORMAT.get().format(timeId);
                int count = authorityUserRepository.updateUserPassword(userId, username, encode, nowTime);
                securityResponse.setSuccess(true);
                securityResponse.setCode("1");
                securityResponse.setMessage("updateUserPassword success!!");
                securityResponse.setData("updateUserPassword success!!");
                securityResponse.setTotal(count);
            } else {
                securityResponse.setMessage("Incomplete information!!");
                securityResponse.setData("Incomplete information!!");
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            securityResponse.setMessage("updateUserPassword failure!!");
            securityResponse.setData("updateUserPassword failure!!");
        }
        return securityResponse;
    }
}
