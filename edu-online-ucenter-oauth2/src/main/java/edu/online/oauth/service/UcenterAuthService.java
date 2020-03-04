package edu.online.oauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.online.Entity.ucenter.VO.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Classname UcenterAuthService
 * @Description TODO
 * @Date 2020/3/2 20:50
 * @Created by zhoutao
 */
@Service
public class UcenterAuthService {
    @Value("${auth.tokenValiditySeconds}")
    int tokenValiditySeconds;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String login(String username, String password, String clientId, String clientSecret) {
        //远程调用security提供的端点获取token
        String authToken = this.applyToken(username, password, clientId, clientSecret);

        //用户身份令牌(短)
        if(authToken.equals("1")||authToken.equals("2")||authToken.equals("0")){
            return authToken;
        }
        UserToken userToken = JSON.parseObject(authToken, UserToken.class);
        String access_token = userToken.getAccess_token();
        //将令牌存储到redis
        boolean result = this.saveTokenToRedis(access_token, authToken, tokenValiditySeconds);
        if (!result) {
            return "3";
        }
        return authToken;
    }

    /**
     * @param access_token 用户身份令牌
     * @param content      内容就是AuthToken对象的内容
     * @param ttl          过期时间
     * @return
     */
    private boolean saveTokenToRedis(String access_token, String content, int ttl) {
        String key = "user_token:" + access_token;
        stringRedisTemplate.boundValueOps(key).set(content, ttl, TimeUnit.SECONDS);//单位秒
        Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS); //获取这个key的过期时间
        return expire > 0; //表示存储成功
    }

    /**
     * @return edu.online.Entity.ucenter.VO.UserToken
     * @Description LoadBalancerClient+restTemplate远程调用security提供的端点
     * @Param [username, password, clientId, clientSecret]
     **/
    private String applyToken(String username, String password, String clientId, String clientSecret) {
        ServiceInstance instance = loadBalancerClient.choose("edu-online-oauth-oauth2");
        //此地址就是http://ip:port
        URI uri = instance.getUri();
        //令牌申请的地址 http://localhost:41001/auth/oauth/token
        String authUrl = uri + "/auth/oauth/token";
        //获取httpbasic认证需要的参数
        String httpBasic = getHttpBasic(clientId, clientSecret);
        //定义header
        LinkedMultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("Authorization", httpBasic);
        //定义body
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", username);
        body.add("password", password);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(body, header);
        //设置restTemplate远程调用时候，对400和401不让报错，正确返回数据
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400 && response.getRawStatusCode() != 401) {
                    super.handleError(response);
                }
            }
        });
        //远程调用
        ResponseEntity<Map> map = restTemplate.exchange(authUrl, HttpMethod.POST, httpEntity, Map.class);
        Map mapContent = map.getBody();
        if (mapContent == null ||   //有一个值为空 就算申请失败
                mapContent.get("jti") == null ||
                mapContent.get("access_token") == null ||
                mapContent.get("refresh_token") == null) {
            String error_description = (String)mapContent.get("error_description");
            if (mapContent != null && error_description.indexOf("坏的凭证")>=0 ) {
                return "1"; //密码错误
            }
            if (mapContent != null && error_description.indexOf("UserDetailsService returned null")>=0) {
                return "2"; //账号不存在
            }
            return "0";  //申请令牌失败
        }
        UserToken authToken = new UserToken();
        authToken.setAccess_token((String) mapContent.get("jti")); //短令牌
        authToken.setJwt_token((String) mapContent.get("access_token")); //jwt令牌
        authToken.setRefresh_token((String) mapContent.get("refresh_token"));
        return JSONObject.toJSONString(authToken);
    }

    /*拼接 Basic ASCHJFDUWF= 这种形式*/
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);//主要Basic后面有个空格
    }
}
