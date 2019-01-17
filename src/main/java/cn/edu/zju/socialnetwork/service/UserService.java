package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.MessageWithLike;
import cn.edu.zju.socialnetwork.response.ResponseMessages;

import java.util.List;

public interface UserService {

    // 用户注册
    String register(RegisterUserInfo user);

    // 判断邮箱是否重复
    Boolean isValidEmail(String account);

    // 用户登录
    String login(String account, String password);

    // 获取用户所有留言
    ResponseMessages getMessages(String ownerAccount, String visitorAccount);

}
