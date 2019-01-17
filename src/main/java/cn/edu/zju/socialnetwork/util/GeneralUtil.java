package cn.edu.zju.socialnetwork.util;

import cn.edu.zju.socialnetwork.entity.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class GeneralUtil {

    public static String getCurrentUserFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
       // System.out.println(cookies.length);
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginAccount")) {
                    return cookie.getValue();
                }
            }
        }
        return "please login first";
    }

    // 判断一个用户是否在用户集合中
    public static boolean isIn(Set<User> users, User thisOne) {
        String email = thisOne.getEmail();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
