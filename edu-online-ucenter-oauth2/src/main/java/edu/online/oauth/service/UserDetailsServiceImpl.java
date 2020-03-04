package edu.online.oauth.service;

import edu.online.Entity.ucenter.Menu;
import edu.online.Entity.ucenter.VO.UserVO;
import edu.online.oauth.config.UserJwt;
import edu.online.oauth.feign.UserFeignClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname UserDetailsServiceImpl
 * @Description 实现spring-security框架提供的UserDetailsService接口
 * @Date 2020/3/2 20:49
 * @Created by zhoutao
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UserFeignClient userFeignClient;

    /**
     * @return org.springframework.security.core.userdetails.UserDetails
     * @Description 重写loadUserByUsername方法 访问/oauth/token端点 验证客户端和用户信息通过才能申请令牌
     * @Param [username]
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有统一采用httpbasic认证，httpbasic中存储了client_id和client_secret 开始认证
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //client_secret
                String clientSecret = clientDetails.getClientSecret();
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        UserVO userVO = userFeignClient.getUser(username);//远程调用用户中心根据账号查询用户信息
        if (userVO == null) {
            //返回空给spring security 抛出异常 表示用户不存在
            return null;
        }
        userVO.setPermissions(new ArrayList<Menu>());//权限暂时用静态的
        String password = userVO.getPassword();//获取数据库中的加密的密码 将在DaoAuthenticationProvider的 additionalAuthenticationChecks方法中校验密码
        //从数据库获取权限
        List<Menu> permissions = userVO.getPermissions();
        List<String> user_permission = new ArrayList<>();
        permissions.forEach(item -> user_permission.add(item.getCode()));
        String user_permission_string = StringUtils.join(user_permission.toArray(), ",");

        UserJwt userDetails = new UserJwt(username,
                password,
                AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
        userDetails.setId(userVO.getId());
        userDetails.setUtype(userVO.getUtype());//用户类型
        userDetails.setCompanyId(userVO.getCompanyId());//所属企业
        userDetails.setName(userVO.getName());//用户名称
        userDetails.setUserpic(userVO.getUserpic());//用户头像
        return userDetails;
    }
}
