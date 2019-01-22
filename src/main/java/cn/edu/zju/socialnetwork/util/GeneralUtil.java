package cn.edu.zju.socialnetwork.util;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.response.AdditionalMessage;
import cn.edu.zju.socialnetwork.response.AdditionalMoment;

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

    // 在动态里添加是否点赞和是否可以删除的信息
    public static List<AdditionalMoment> addInfoIntoMoments(List<Moment> moments, User user){
        List<AdditionalMoment> additionalMoments = new ArrayList<>();
        if(moments != null && moments.size() != 0){
            for (Moment moment : moments) {
                AdditionalMoment rm = new AdditionalMoment(moment.getId(), moment.getOwner().getName(), moment.getOwner().getHeadpic(), moment.getContent(), moment.getTime(), moment.getPic());
                if(moment.getOwner().getEmail().equals(user.getEmail())){
                    rm.setDeletable(true);
                }
                Set<User> likedUsers = moment.getLikedBy();
                if (likedUsers != null && likedUsers.size() != 0) {
                    rm.setLike(likedUsers.size());
                    if (isIn(likedUsers, user)){
                        rm.setLiked(true);
                    }
                }
                additionalMoments.add(rm);
            }
        }
        return additionalMoments;
    }

    // 在留言里添加是否点赞和是否可以删除的信息
    public static List<AdditionalMessage> addInfoIntoMessages(List<Message> messages, User owner, User visitor){
        List<AdditionalMessage> additionalMessages = new ArrayList<>();
        if (messages.size() != 0) {
            for (Message m : messages) {
                User messageOwner = m.getOwner();
                AdditionalMessage rm = new AdditionalMessage(m.getId(), messageOwner.getName(), messageOwner.getHeadpic(), m.getText(), m.getTime());
                Set<User> likedUsers = m.getLikedBy();
                if (likedUsers != null && likedUsers.size() != 0) {
                    rm.setLike(likedUsers.size());
                    rm.setLiked(isIn(likedUsers, visitor));
                }
                if (visitor.getEmail().equals(messageOwner.getEmail()) || visitor.getEmail().equals(owner.getEmail())){
                    rm.setDeletable(true);
                }
                additionalMessages.add(rm);
            }
        }
        return additionalMessages;
    }
}
