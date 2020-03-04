package edu.online.Entity.ucenter.VO;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class UserToken {
    String access_token;//访问token就是短令牌，用户身份令牌
    String refresh_token;//刷新token
    String jwt_token;//jwt令牌
}
