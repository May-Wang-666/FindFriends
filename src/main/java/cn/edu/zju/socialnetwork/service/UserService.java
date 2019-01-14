package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.HomePageInfo;

import java.util.List;

public interface UserService {

    // 用户注册
    String register(RegisterUserInfo user);

    // 用户登录
    String login(String account, String password);

    // 获取用户主页信息
    HomePageInfo getUserInfo(String account);

}
