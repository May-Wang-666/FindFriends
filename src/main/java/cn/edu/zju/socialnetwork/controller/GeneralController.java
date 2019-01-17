package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/look")
//@CrossOrigin
public class GeneralController {

    @Autowired
    UserService userService;

    @Autowired
    GeneralService generalService;

    @RequestMapping(value = "/homepage")
    public HomePageInfo homePage(@RequestBody String owner, HttpServletRequest request){
        System.out.println("收到homepage请求：");
        String visitor = GeneralUtil.getCurrentUserFromCookie(request);
        System.out.println(visitor);
        if (visitor.equals("please login first")){
            return null;
        }
        return generalService.getHomePage(owner,visitor);
    }

    // 获取留言列表
    @RequestMapping(value = "/messages")
    public ResponseMessages getMessages(@RequestBody String ownerAccount, HttpServletRequest request) {
        System.out.println("收到message请求");
        // 获取当前登录的人
        String visitor = GeneralUtil.getCurrentUserFromCookie(request);{
            if (visitor.equals("please login first")){
                return null;
            }
        }
        return userService.getMessages(ownerAccount, visitor);
    }
}
