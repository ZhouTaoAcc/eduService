package edu.online.ucenter.service;

import edu.online.Entity.ucenter.CompanyUser;
import edu.online.Entity.ucenter.User;
import edu.online.Entity.ucenter.VO.UserVO;
import edu.online.ucenter.dao.CompanyUserRepository;
import edu.online.ucenter.dao.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2020/3/3 13:43
 * @Created by zhoutao
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CompanyUserRepository companyUserRepository;

    //登录时需要远程调用的接口
    public UserVO getUser(String username) {
        //查询用户基本信息
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        String userId = user.getId();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        //查询用户所属企业id
        String companyId = null;
        CompanyUser companyUser = companyUserRepository.findByUserId(userId);
        if (companyUser != null) {
            companyId = companyUser.getCompanyId();
            userVO.setCompanyId(companyId);
        }
        return userVO;
    }

    //根据用户id查询用户信息
    public UserVO findUserById(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userId = user.getId();
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            //查询用户所属企业id
            String companyId = null;
            CompanyUser companyUser = companyUserRepository.findByUserId(userId);
            if (companyUser != null) {
                companyId = companyUser.getCompanyId();
                userVO.setCompanyId(companyId);
            }
            return userVO;
        }
        return null;
    }
}
