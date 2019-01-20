package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;

import java.util.List;

public interface UserService {

    // 判断邮箱是否重复
    Boolean isValidEmail(String account);

    // 用户注册
    String register(RegisterUserInfo user);

    // 用户登录
    String login(String account, String password);

    // 根据邮箱或姓名查找用户
    List<User> findFriends(String keyWord);

    // 一个用户关注另一个用户
    void follow(String followedAccount, String followerAccount);

    // 一个用户取消关注另一个用户
    void unFollow(String followedAccount, String followerAccount);

    // 修改用户头像
    String modifyHeadPic(String account, String dataURL);

    // 修改用户个人信息
    String updatePersonalInfo(String account,String nickname,String sex, String xinzuo, int age, String motto);


    // 根据Email查找用户
    User findByAccount(String account);

    // 根据名字查找用户
    List<User> findByName(String name);

}
