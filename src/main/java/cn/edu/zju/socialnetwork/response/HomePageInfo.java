package cn.edu.zju.socialnetwork.response;

import cn.edu.zju.socialnetwork.entity.User;

import java.util.ArrayList;
import java.util.List;

public class HomePageInfo {

    // 个人信息
    private Person personal;
    // 好友列表
    private List<Friend> friends;
    // 统计信息
    private StaticInfo statics;
    // 动态列表
    private List<ResponseMoment> dongtailist;

    // 传入当前用户、好友用户列表、动态数、留言数、本人留言列表
    public HomePageInfo(User user, List<User> friendList, int numOfMoments, int numOfMessages, List<ResponseMoment> moments) {
        personal = new Person(user.getNickname(), user.getHeadpic(), user.getMotto(), user.getSex(), user.getAge(), user.getXinzuo(), user.getEmail());
        friends = new ArrayList<>();
        for(User friend:friendList){
            Friend tmp = new Friend(friend.getNickname(),friend.getEmail(),friend.getHeadpic());
            friends.add(tmp);
        }
        statics = new StaticInfo(numOfMoments, numOfMessages);
        dongtailist = moments;
    }

    private class Person {
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

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public void setMotto(String motto) {
            this.motto = motto;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setXinzuo(String xinzuo) {
            this.xinzuo = xinzuo;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    private class Friend{
        private String nickname;
        private String email;
        private String headpic;

        public Friend(String nickname, String email, String headpic) {
            this.nickname = nickname;
            this.email = email;
            this.headpic = headpic;
        }
    }

    private class StaticInfo {
        private int dongtai;
        private int liuyan;

        public StaticInfo(int dongtai, int liuyan) {
            this.dongtai = dongtai;
            this.liuyan = liuyan;
        }
    }
}
