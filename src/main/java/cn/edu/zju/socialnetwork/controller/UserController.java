package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    Environment env;

    @Autowired
    UserService userService;

    // 测试上传文件接口
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String testUploadingImg(@RequestBody String dataURL) {
        System.out.println("传入数据：");
        System.out.println(dataURL);
        String localURL = ImageUtil.saveBase64Image(dataURL, env.getProperty("upload.path"));
        System.out.println("成功保存图片：" + localURL);
        return localURL;
    }

    // 判断邮箱地址是否已被使用
    // 已使用返回false，未使用返回true
    @RequestMapping(value = "/validemail")
    public Boolean isValidEmail(@RequestBody String email) {
        return userService.isValidEmail(email);
    }

    // 注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody RegisterUserInfo userInfo) {
        System.out.println(userInfo);
        String res = userService.register(userInfo);
        System.out.println(res);
        if (res.equals("internal error")) {
            return "sorry, server sucks";
        }
        return "success";
    }

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody HashMap<String,String> loginInfo, HttpServletResponse response) {
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
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            cookies[0].setMaxAge(0); // 使cookie失效
        }
        return;
    }

    // 查找好友


}
