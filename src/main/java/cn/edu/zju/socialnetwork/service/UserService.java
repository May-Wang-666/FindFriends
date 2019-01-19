package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;

public interface UserService {

    // 用户注册
    String register(RegisterUserInfo user);

    // 判断邮箱是否重复
    Boolean isValidEmail(String account);

    // 用户登录
    String login(String account, String password);

    // 寻找用户
    User findByAccount(String account);

}
