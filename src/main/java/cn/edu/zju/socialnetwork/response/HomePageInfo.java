package cn.edu.zju.socialnetwork.response;

import cn.edu.zju.socialnetwork.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class HomePageInfo {

    // 个人信息
    private Person personal;
    // 好友列表
    private List<Friend> friends;
    // 统计信息
    private StaticInfo statics;
    // 动态列表
    private List<MomentWithLike> dongtailist;
    // 是否最后一页，默认为false
    private boolean isLastPageOfMoment;


    public HomePageInfo(){}

    // 传入当前用户、好友用户列表、动态数、留言数、动态列表
    public HomePageInfo(User user, List<User> friendList, int numOfMoments, int numOfMessages, List<MomentWithLike> moments) {
        personal = new Person(user.getName(), user.getHeadpic(), user.getMotto(), user.getSex(), user.getAge(), user.getXinzuo(), user.getEmail());
        friends = new ArrayList<>();
        if (friendList != null){
            for(User friend:friendList){
                Friend tmp = new Friend(friend.getName(),friend.getEmail(),friend.getHeadpic());
                friends.add(tmp);
            }
        }
        statics = new StaticInfo(numOfMoments, numOfMessages);
        dongtailist = moments;
        isLastPageOfMoment = false;
    }

    public HomePageInfo(Person personal, List<Friend> friends, StaticInfo statics, List<MomentWithLike> dongtailist, boolean isLastPageOfMoment) {
        this.personal = personal;
        this.friends = friends;
        this.statics = statics;
        this.dongtailist = dongtailist;
        this.isLastPageOfMoment = isLastPageOfMoment;
    }

    public void setLastPageOfMoment(boolean lastPageOfMoment) {
        isLastPageOfMoment = lastPageOfMoment;
    }

    @Getter
    private static class Person {
        private String nickname;
        private String headpic;
        private String motto;
        private String sex;
        private int age;
        private String xinzuo;
        private String email;

        public Person() {
        }

        public Person(String nickname, String headpic, String motto, String sex, int age, String xinzuo, String email) {
            this.nickname = nickname;
            this.headpic = headpic;
            this.motto = motto;
            this.sex = sex;
            this.age = age;
            this.xinzuo = xinzuo;
            this.email = email;
        }
    }

    @Getter
    private static class Friend{
        private String nickname;
        private String email;
        private String headpic;

        public Friend(){}

        public Friend(String nickname, String email, String headpic) {
            this.nickname = nickname;
            this.email = email;
            this.headpic = headpic;
        }
    }

    @Getter
    private static class StaticInfo {
        private int dongtai;
        private int liuyan;

        public StaticInfo(){}

        public StaticInfo(int dongtai, int liuyan) {
            this.dongtai = dongtai;
            this.liuyan = liuyan;
        }
    }

    @Override
    public String toString() {
        return "HomePageInfo{" +
                "personal=" + personal +
                ", friends=" + friends +
                ", statics=" + statics +
                ", dongtailist=" + dongtailist +
                '}';
    }
}
