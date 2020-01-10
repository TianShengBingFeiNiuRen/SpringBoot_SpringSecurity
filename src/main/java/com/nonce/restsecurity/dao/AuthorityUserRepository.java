package com.nonce.restsecurity.dao;

import com.nonce.restsecurity.domain.AuthorityUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Andon
 * @date 2019/3/20
 */
@Repository
public interface AuthorityUserRepository extends JpaRepository<AuthorityUser, Integer> {

    @Query(nativeQuery = true, value = "SELECT password FROM authority_user WHERE username=?1 AND valid_time>?2")
    String findPasswordByUsernameAfterValidTime(String username, String validTime);

    @Query(nativeQuery = true, value = "SELECT role_name FROM authority_role WHERE id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1))")
    List<String> findRoleNameByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT id,nickname,remark FROM authority_user WHERE username=?1")
    Map<String, Object> findUserIdAndNickNameAndRemarkByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT url FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1)))")
    List<String> findUrlsByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre, remark AS remark FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1))) AND parent_id=0")
    List<Map<String, Object>> findRootMenuInfoByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1))) AND parent_id!=0")
    List<Map<String, Object>> findNotRootMenuInfoByUsername(String username);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu WHERE parent_id=?1")
    List<Map<String, Object>> findMenuInfoByParentId(int id);

    @Query(nativeQuery = true, value = "SELECT DISTINCT(parent_id) FROM authority_menu \n" +
            "\tWHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id=?1) \n" +
            "\tAND parent_id NOT IN (SELECT id FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id=?1) AND parent_id=0)\n" +
            "\tAND parent_id NOT IN (0,1)")
    List<Integer> findRootMenuIdOfPartialPermissionByRoleId(int roleId);

    @Query(nativeQuery = true, value = "SELECT DISTINCT(parent_id) FROM authority_menu \n" +
            "\tWHERE parent_id NOT IN (SELECT id FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1))) AND parent_id=0) \n" +
            "\tAND id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1)))\n" +
            "\tAND parent_id NOT IN (0,1)")
    List<Integer> findRootMenuIdOfPartialPermission(String username);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre, remark AS remark FROM authority_menu WHERE id=?1")
    Map<String, Object> findMenuInfoByMenuId(int menuId);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu \n" +
            "\tWHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id=?1)\n" +
            "\tAND parent_id=?2")
    List<Map<String, Object>> findChildrenMenuInfoByRoleIdAndParentId(int roleId, int parentId);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu \n" +
            "\tWHERE id IN (SELECT DISTINCT(id) FROM authority_menu \n" +
            "\t\tWHERE parent_id NOT IN (SELECT id FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1))) AND parent_id=0) \n" +
            "\t\tAND id IN (SELECT menu_id FROM authority_role_menu WHERE role_id IN (SELECT role_id FROM authority_user_role WHERE user_id=(SELECT id FROM authority_user WHERE username=?1)))\n" +
            "\t\tAND parent_id NOT IN (0,1))\n" +
            "\tAND parent_id=?2")
    List<Map<String, Object>> findChildrenMenuInfoByUsernameAndParentId(String username, int parentId);

    @Query(nativeQuery = true, value = "SELECT url FROM authority_menu")
    List<String> findAllMenuUrl();

    @Query(nativeQuery = true, value = "SELECT role_name FROM authority_role WHERE id IN (SELECT role_id FROM authority_role_menu WHERE menu_id IN (SELECT id FROM authority_menu WHERE url=?1))")
    List<String> findRoleNameByMenuUrl(String url);

    @Query(nativeQuery = true, value = "SELECT COUNT(username) FROM authority_user WHERE username=?1")
    int findCountByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO authority_user (nickname, username, password, email, phone, valid_time, update_time, remark) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8)")
    void addUserInfo(String nickname, String username, String password, String email, String phone, String validTime, String nowTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO authority_user_role(user_id, role_id, update_time) VALUES(?1,?2,?3)")
    int addRoleForUser(int userId, int roleId, String updateTime);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_user_role WHERE user_id=?1")
    int deleteRolesByUserId(int userId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_user WHERE id=?1")
    int deleteUserInfoByUserId(int userId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?2, username=?3, password=?4, email=?5, phone=?6, valid_time=?7, update_time=?8, remark=?9 WHERE id=?1")
    int updateUserInfoByUserId(int id, String nickname, String username, String password, String email, String phone, String validTime, String updateTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?2, username=?3, password=?4, email=?5, phone=?6, update_time=?7, remark=?8 WHERE id=?1")
    int updateUserInfoByUserIdExcludeValidTime(int id, String nickname, String username, String password, String email, String phone, String updateTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?2, username=?3, email=?4, phone=?5, update_time=?6, remark=?7 WHERE id=?1")
    int updateUserInfoByUserIdExcludeValidTimeAndPassword(int id, String nickname, String username, String email, String phone, String updateTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?2, username=?3, email=?4, phone=?5, valid_time=?6, update_time=?7, remark=?8 WHERE id=?1")
    int updateUserInfoByUserIdExcludePassword(int id, String nickname, String username, String email, String phone, String validTime, String updateTime, String remark);

    @Query(nativeQuery = true, value = "SELECT id AS id, username AS username, password AS password, nickname AS nickname, email AS email, phone AS phone, valid_time AS validTime, remark AS remark FROM authority_user WHERE username LIKE %?3% AND nickname LIKE %?4% LIMIT ?1,?2")
    List<Map<String, Object>> findAllUserInfo(int pageNum, int pageSize, String username, String nickname);

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM authority_user WHERE username LIKE %?1% AND nickname LIKE %?2%")
    int findAllUserInfoSize(String username, String nickname);

    @Query(nativeQuery = true, value = "SELECT id AS id, role_name AS roleName, role_name_CN AS roleNameCN, remark AS remark FROM authority_role WHERE id IN (SELECT role_id FROM authority_user_role WHERE user_id=?1)")
    List<Map<String, Object>> findRoleInfoByUserId(int userId);

    @Query(nativeQuery = true, value = "SELECT COUNT(role_name) FROM authority_role WHERE role_name=?1")
    int roleNameIsExistence(String roleName);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO authority_role(role_name, role_name_CN, update_time, remark) VALUES (?1, ?2, ?3, ?4)")
    void addRoleInfo(String roleName, String roleNameCN, String nowTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO authority_role_menu(role_id, menu_id, update_time) VALUES(?1,?2,?3)")
    int addMenuForRole(int roleId, int menuId, String updateTime);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_role_menu WHERE role_id=?1")
    int deleteMenusByRoleId(int roleId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_role WHERE id=?1")
    int deleteRoleInfoByRoleId(int roleId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_role SET role_name=?2, role_name_CN=?3, update_time=?4, remark=?5 WHERE id=?1")
    int updateRoleInfoByRoleId(int roleId, String roleName, String roleNameCN, String nowTime, String remark);

    @Query(nativeQuery = true, value = "SELECT id AS id, role_name AS roleName, role_name_CN AS roleNameCN, remark AS remark FROM authority_role WHERE role_name_CN LIKE ?3 LIMIT ?1,?2")
    List<Map<String, Object>> findAllRoleInfo(int pageNum, int pageSize, String roleNameCN);

    @Query(nativeQuery = true, value = "SELECT COUNT(id) FROM authority_role WHERE role_name_CN LIKE ?1")
    int findAllRoleInfoSize(String roleNameCN);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id=?1) AND parent_id IN (0,1)")
    List<Map<String, Object>> findRootMenuInfoByRoleId(int roleId);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre FROM authority_menu WHERE id IN (SELECT menu_id FROM authority_role_menu WHERE role_id=?1) AND parent_id!=0")
    List<Map<String, Object>> findNotRootMenuInfoByRoleId(int roleId);

    @Query(nativeQuery = true, value = "SELECT COUNT(menu_name) FROM authority_menu WHERE menu_name=?1 AND parent_id=?2")
    int menuNameIsExistence(String menuName, String parentId);

    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO authority_menu (url, menu_name, parent_id, update_time, remark, url_pre) VALUES (?1, ?2, ?3, ?4, ?5, ?6)")
    void addMenuInfo(String url, String menuName, String parentId, String updateTime, String remark, String urlPre);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, remark AS remark, url_pre AS urlPre FROM authority_menu WHERE menu_name=?1 AND parent_id=?2")
    Map<String, String> findMenuInfoByMenuName(String menuName, String parentId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_role_menu WHERE menu_id=?1")
    int deleteRolesByMenuId(int menuId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM authority_menu WHERE id=?1")
    int deleteMenuInfoByMenuId(int menuId);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_menu SET url=?2, menu_name=?3, parent_id=?4, update_time=?5, remark=?6, url_pre=?7 WHERE id=?1")
    int updateMenuInfoByMenuId(int id, String url, String menuName, String parentId, String updateTime, String remark, String urlPre);

    @Query(nativeQuery = true, value = "SELECT id AS id, url AS url, menu_name AS menuName, parent_id AS parentId, url_pre AS urlPre, remark AS remark FROM authority_menu WHERE parent_id=0")
    List<Map<String, Object>> findRootMenuInfo();

    @Query(nativeQuery = true, value = "SELECT COUNT(username) FROM authority_user WHERE id!=?1 AND username=?2")
    int isNotExistenceOfUpdateUsername(int userId, String username);

    @Query(nativeQuery = true, value = "SELECT COUNT(role_name) FROM authority_role WHERE id!=?1 AND role_name=?2")
    int isNotExistenceOfUpdateRoleName(int roleId, String roleName);

    @Query(nativeQuery = true, value = "SELECT COUNT(menu_name) FROM authority_menu WHERE id!=?1 AND menu_name=?2 AND parent_id=?3")
    int isNotExistenceOfUpdateMenuName(int menuId, String menuName, String parentId);

    @Query(nativeQuery = true, value = "SELECT id FROM authority_menu WHERE parent_id=?1")
    List<Integer> selectChildrenMenuIds(int parentId);

    @Query(nativeQuery = true, value = "SELECT nickname,username,phone,email FROM authority_user WHERE id=?1 AND username=?2")
    Map<String, String> getUserInfoByUserIdAndUsername(String userId, String username);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?1, email=?3, phone=?4, update_time=?5 WHERE username=?2")
    int updateUserBasicInfo(String nickname, String username, String email, String phone, String updateTime);

    @Query(nativeQuery = true, value = "SELECT `password` FROM authority_user WHERE username=?1")
    String getUserPasswordByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET `password`=?2, update_time=?3 WHERE username=?1")
    int updateUserPasswordByUsername(String username, String newPassword, String updateTime);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET nickname=?1, email=?3, phone=?4, valid_time=?5, update_time=?6, remark=?7 WHERE username=?2")
    int updateUserInfo(String nickname, String username, String email, String phone, String validTime, String updateTime, String remark);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE authority_user SET `password`=?3, update_time=?4 WHERE id=?1 AND username=?2")
    int updateUserPassword(String userId, String username, String password, String updateTime);
}
