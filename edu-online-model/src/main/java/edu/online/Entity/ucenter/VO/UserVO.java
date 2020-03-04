package edu.online.Entity.ucenter.VO;

import edu.online.Entity.ucenter.Menu;
import edu.online.Entity.ucenter.User;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;

/**
 * @Classname UserVO
 * @Description VO层 user实体的扩展类
 * @Date 2020/3/3 13:47
 * @Created by zhoutao
 */
@Data
@ToString
public class UserVO extends User {
   //用户拥有的权限
    private ArrayList<Menu> permissions;
    //用户所属企业id
    private String companyId;

}
