package edu.online.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname CookieUtil
 * @Description TODO
 * @Date 2019/12/7 21:12
 * @Created by zhoutao
 */
public class CookieUtil {
 /*
  * @Description 设置cookie
  * @param response
  * @param name     cookie名字
  * @param value    cookie值
  * @param maxAge   cookie生命周期 以秒为单位
  **/
    public static void addCookie(HttpServletResponse response,String name,String path,
                                 String value,String domain,int maxAge,boolean httpOnly){
        Cookie cookie=new Cookie(name,value);
        cookie.setDomain(domain); //跨域共享cookie
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly); //Cookie中设置了"HttpOnly"属性 true，通过JavaScript脚本将无法读取到Cookie信息
        cookie.setPath(path); //同一应用服务器内共享
        response.addCookie(cookie);
    }
     /*
      * @Description //TODO
      * @param request
      * @param cookieName1,cookieName2
      * @return map<cookieName,cookieValue>
      **/
    public static Map<String,String> readCookie(HttpServletRequest request, String ... cookieNames) {
        Map<String,String> cookieMap = new HashMap<String,String>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                String cookieValue = cookie.getValue();
                for(int i=0;i<cookieNames.length;i++){
                    if(cookieNames[i].equals(cookieName)){
                        cookieMap.put(cookieName,cookieValue);
                    }
                }
            }
        }
        return cookieMap;

    }

}
