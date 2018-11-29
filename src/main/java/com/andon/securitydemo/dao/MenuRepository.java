package com.andon.securitydemo.dao;

import com.andon.securitydemo.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByParentId(Long id);

    Menu findByMenuName(String menuName);

    List<Menu> findByUrl(String url);

}
