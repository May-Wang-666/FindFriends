package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.MessageWithLike;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private String defaultHeadpic = "/headpics/default_headpic.png";


    /**
     * 用户注册
     *
     * @param userInfo 传进的参数
     * @return
     */
    @Override
    public String register(RegisterUserInfo userInfo) {
        String account = userInfo.getEmail();
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("获取本机ip失败");
            e.printStackTrace();
            return "internal error";
        }
        String headpic = "http://" + localHost + ":8080" + defaultHeadpic;
        User newUser = new User(userInfo.getEmail(), userInfo.getPassword(), userInfo.getNickname(), headpic, userInfo.getMotto(), userInfo.getSex(), userInfo.getAge(), userInfo.getXinzuo());
        userRepository.save(newUser);
        return "success";
    }

    @Override
    public Boolean isValidEmail(String email) {
        // 相同邮箱不允许重复注册
        if (userRepository.findByEmail(email) != null) {
            return false;
        }
        return true;
    }

    /**
     * 用户登录
     * 用户是否存在 → 密码是否正确
     *
     * @param account
     * @param password
     * @return 用户不存在：user does not exist
     * 密码不正确：incorrect password
     * 用户、密码正确：success
     */
    @Override
    public String login(String account, String password) {
        User user = userRepository.findByEmail(account);
        if (user == null) {
            return "user does not exist";
        } else if (!user.getPassword().equals(password)) {
            return "incorrect password";
        }
        return user.getEmail();
    }

    @Override
    public User findByAccount(String account) {
        return userRepository.findByEmail(account);
    }

}
