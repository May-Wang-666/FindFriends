package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.FriendInfo;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import cn.edu.zju.socialnetwork.util.StaticValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Environment env;


    /**
     * 用户注册
     * @param userInfo 传进的参数
     * @return
     */
    @Override
    public String register(RegisterUserInfo userInfo) {
        String headpic = StaticValues.defaultHeadpic;
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

    /**
     * 根据用户名/用户邮箱查找用户
     * @param keyWord
     * @return
     */
    @Override
    public List<FriendInfo> findFriends(String keyWord, String currentAccount) {
        List<User> res = new ArrayList<>();
        User byEmail = findByAccount(keyWord);
        if (byEmail != null) {
            res.add(byEmail);
        }
        List<User> byName = findByName(keyWord);
        if (byName != null && byName.size() != 0) {
            res.addAll(byName);
        }
        // 返回查找到的用户信息
        List<FriendInfo> friendInfos = new ArrayList<>();
        if (res.size() != 0) {
            User currentUser = userRepository.findByEmail(currentAccount);
            Set<User> myFriends = currentUser.getFriends();
            for (User user : res) {
                FriendInfo info = new FriendInfo(user.getEmail(), user.getName(), user.getHeadpic());
                info.setFriends(GeneralUtil.isIn(myFriends, user));
                friendInfos.add(info);
            }
        }
        return friendInfos;
    }

    // 关注用户
    @Override
    public void follow(String followedAccount, String followerAccount) {
        User followed = userRepository.findByEmail(followedAccount);
        User follwer = userRepository.findByEmail(followerAccount);
        follwer.follow(followed);
        userRepository.save(follwer);
    }

    // 取消关注
    @Override
    public void unFollow(String followedAccount, String followerAccount) {
        User followed = userRepository.findByEmail(followedAccount);
        User follwer = userRepository.findByEmail(followerAccount);
        follwer.unFollow(followed);
        userRepository.save(follwer);
    }

    // 修改用户头像
    @Override
    public String modifyHeadPic(String account, String dataURL) {
        String newPicURL = ImageUtil.saveBase64Image(dataURL, env.getProperty("upload.path"));
        userRepository.modifyHeadpic(account, newPicURL);
        return newPicURL;
    }

    // 修改个人信息
    @Override
    public String updatePersonalInfo(String account, String nickname, String sex, String xinzuo, int age, String motto) {
        userRepository.updateUserInfo(account, nickname, sex, xinzuo, age, motto);
        return "success";
    }


    // 根据邮箱查找用户/获取用户个人信息
    @Override
    public User findByAccount(String account) {
        return userRepository.findByEmail(account);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findAllByName(name);
    }

}
