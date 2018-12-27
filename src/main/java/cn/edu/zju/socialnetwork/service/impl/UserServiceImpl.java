package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String addUser(User user) {

        // 账户为空，不允许添加
        if(user.getAccount() == null || user.getAccount().equals("")){
            System.out.println("账户为空");
            return "empty account";
        }
        // 密码为空，不允许添加
        if (user.getPassword() == null || user.getPassword().equals("")){
            System.out.println("密码为空");
            return "empty password";
        }
        // 判断如果用户账户已存在，不允许添加
        String account = user.getAccount();
        List<User> userList = userRepository.getAllUser();
        for (User existedUser:userList){
            if (existedUser.getAccount().equals(account)){
                System.out.println("账户已存在，添加失败！");
                return "user account already exists";
            }
        }
        userRepository.save(user);
        return "user added";
    }

    @Override
    public List<User> getUserByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User getUserByAccount(String account) {
        return userRepository.findByAccount(account);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUser();
    }

    @Override
    public User modifyName(String account, String newName) {
        return null;
    }

    @Override
    public User modifyPassword(String account, String newPassword) {
        return null;
    }
}
