package cn.edu.zju.socialnetwork.util;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.response.MomentWithLike;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GeneralUtil {

    public static String getCurrentUserFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getValue());
                if (cookie.getName().equals("loginAccount")) {
                    return cookie.getValue();
                }
            }
        }
        return "please login first";
    }

    // 拒绝处理没有携带cookie的请求
    public static void CheckCookie(String cookieStatus, HttpServletResponse response){

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

    // 在动态里添加是否点赞的信息
    public static List<MomentWithLike> addLikeInfoIntoMoments(List<Moment> moments, User user){
        List<MomentWithLike> momentWithLikes = new ArrayList<>();
        if(moments != null && moments.size() != 0){
            for (Moment moment : moments) {
                MomentWithLike rm = new MomentWithLike(moment.getId(), moment.getOwner().getName(), moment.getOwner().getHeadpic(), moment.getContent(), moment.getTime(), moment.getPic());
                Set<User> likedUsers = moment.getLikedBy();
                if (likedUsers != null && likedUsers.size() != 0) {
                    rm.setLike(likedUsers.size());
                    if (isIn(likedUsers, user)){
                        rm.setLiked(true);
                    }
                }
                momentWithLikes.add(rm);
            }
        }
        return momentWithLikes;
    }
}
