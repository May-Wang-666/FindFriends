package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.service.UserService;
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
     * 相同邮箱不允许重复注册
     * @param userInfo 传进的参数
     * @return
     */
    @Override
    public String register(RegisterUserInfo userInfo) {
        String account = userInfo.getEmail();
        // 相同邮箱不允许重复注册
        if(userRepository.findByEmail(account) != null){
            return "invalid email";
        }
        String localHost = "";
        try {
            localHost = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("获取本机ip失败");
            e.printStackTrace();
            return "internal error";
        }
        String headpic = "http://"+localHost+":8080"+defaultHeadpic;
        User newUser = new User(userInfo.getEmail(),userInfo.getPassword(),userInfo.getNickname(),headpic,userInfo.getMotto(),userInfo.getSex(),userInfo.getAge(),userInfo.getXinzuo());
        userRepository.save(newUser);
        return "success";
    }

    @Override
    public String login(String account, String password) {
        return null;
    }

    @Override
    public HomePageInfo getUserInfo(String account) {
        return null;
    }
}
