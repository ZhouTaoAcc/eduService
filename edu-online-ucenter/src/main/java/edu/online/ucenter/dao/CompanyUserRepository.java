package edu.online.ucenter.dao;

import edu.online.Entity.ucenter.CompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname CompanyUserRepository
 * @Description TODO
 * @Date 2020/3/3 13:37
 * @Created by zhoutao
 */
@Repository
public interface CompanyUserRepository extends JpaRepository<CompanyUser,String> {
    CompanyUser findByUserId(String userId);
}
