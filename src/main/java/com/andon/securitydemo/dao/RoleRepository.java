package com.andon.securitydemo.dao;

import com.andon.securitydemo.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByRoleNameOrRoleNameCN(String roleName, String roleNameCN);
}
