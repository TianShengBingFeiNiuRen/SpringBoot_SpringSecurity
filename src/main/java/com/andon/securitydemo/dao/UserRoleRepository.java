package com.andon.securitydemo.dao;

import com.andon.securitydemo.domain.UserRole;
import com.andon.securitydemo.domain.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {

    Integer deleteByUserId(Long userId);

    Integer deleteByRoleId(Long roleId);
}
