package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.User;

import java.util.List;

public interface UserService {

    // 用户注册，添加用户
    String addUser(User user);

    // 根据用户名获取用户
    List<User> getUserByName(String name);

    // 根据用户账号获取用户
    User getUserByAccount(String account);

    // 获取数据库中所有用户
    List<User> getAllUsers();

    // 修改用户名称
    User modifyName(String account, String newName);

    // 修改用户密码
    User modifyPassword(String account, String newPassword);
}
