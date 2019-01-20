package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.response.FriendInfo;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    Environment env;

    @Autowired
    UserService userService;

    // 判断邮箱地址是否已被使用
    // 已使用返回false，未使用返回true
    @RequestMapping(value = "/validemail")
    public Boolean isValidEmail(@RequestBody String email) {
        return userService.isValidEmail(email);
    }

    // 注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody RegisterUserInfo userInfo) {
        String res = userService.register(userInfo);
        System.out.println("收到注册请求");
        if (res.equals("internal error")) {
            return "sorry, server sucks";
        }
        return "success";
    }

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody HashMap<String, String> loginInfo, HttpServletResponse response) {
        System.out.println("收到登录请求");
        String email = loginInfo.get("email");
        String password = loginInfo.get("password");
        String res = userService.login(email, password);
        // 登录成功，生成cookie
        if (!res.equals("user does not exist") && !res.equals("incorrect password")) {
            Cookie cookie = new Cookie("loginAccount", email);
            cookie.setMaxAge(60 * 60 * 24); //有效期，一天
            cookie.setPath("/");
            response.addCookie(cookie);
            System.out.println("添加cookie成功");
        }
        System.out.println(res);
        return res;
    }

    // 注销登录
    @RequestMapping(value = "/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("收到退出登录请求");
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            cookies[0].setMaxAge(0); // 使cookie失效
        }
        return;
    }

    // 查找好友
    @RequestMapping(value = "/findfriends")
    public List<FriendInfo> findFriends(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
        System.out.println("收到查找好友请求");
        String keyWord = data.get("keyWord").trim();
        System.out.println(data);
        System.out.println("查找好友：" + keyWord);
        String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
        List<FriendInfo> friends = userService.findFriends(keyWord, currentAccount);
        return  friends;
    }

    // 关注/取消关注好友
    @RequestMapping(value = "/makefriends", method = RequestMethod.POST)
    public String follow(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
        String followedAccount = data.get("email");
        String followerAccount = GeneralUtil.getCurrentUserFromCookie(request);
        boolean isUnfollow = Boolean.parseBoolean(data.get("isDelete"));
        System.out.println("收到加好友请求，isDelete: " + isUnfollow);
        if (isUnfollow) {
            userService.unFollow(followedAccount, followerAccount);
        } else {
            userService.follow(followedAccount, followerAccount);
        }
        return "success";
    }

    // 显示个人档
    @RequestMapping(value = "/personal", method = RequestMethod.GET)
    public User getUserInfo(HttpServletRequest request) {
        System.out.println("收到个人档请求");
        String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
        return userService.findByAccount(currentAccount);
    }

    // 修改头像
    @RequestMapping(value = "/headpic", method = RequestMethod.POST)
    public HashMap<String, String> modifyHeadpic(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
        System.out.println("收到换头像请求");
        String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
        String dataURL = data.get("pic");
        String pic = userService.modifyHeadPic(currentAccount, dataURL);
        HashMap<String, String> map = new HashMap<>();
        map.put("newPic", pic);
        return map;
    }

    // 修改个人信息
    @RequestMapping(value = "/personal", method = RequestMethod.POST)
    public String updatePersonalInfo(@RequestBody HashMap<String, String> data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("收到修改个人信息请求");
        System.out.println(data);
        String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
        String nickname = data.get("nickname");
        String sex = data.get("sex");
        String xinzuo = data.get("xinzuo");
        String motto = data.get("motto");
        String ageStr = data.get("age");
        if (ageStr == null || ageStr.equals("")){
            response.sendError(488,"please tell me how old are you");
            return "invalid parameter";
        }
        int age = Integer.valueOf(ageStr.trim());
        return userService.updatePersonalInfo(currentAccount, nickname, sex, xinzuo, age, motto);
    }
}
