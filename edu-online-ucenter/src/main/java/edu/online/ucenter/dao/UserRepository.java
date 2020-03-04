package edu.online.ucenter.dao;

import edu.online.Entity.ucenter.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname UserRepository
 * @Description TODO
 * @Date 2020/3/3 13:34
 * @Created by zhoutao
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
}
