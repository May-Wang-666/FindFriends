package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/look")
//@CrossOrigin
public class GeneralController {

    @Autowired
    UserService userService;

    @Autowired
    GeneralService generalService;

    @RequestMapping(value = "/homepage")
    public HomePageInfo homePage(@RequestBody HashMap<String,String> data, HttpServletRequest request){
        System.out.println(data);
        String owner = data.get("owner");
        System.out.println("收到homepage请求：");
        System.out.println("主页的主人是："+owner);
        String visitor = GeneralUtil.getCurrentUserFromCookie(request);
        System.out.println("主页访问者："+visitor);
        /*if (visitor.equals("please login first")){
            return null;
        } else {
            System.out.println("从cookie中获取的账号："+visitor);
        }*/
        return generalService.getHomePage(owner,visitor);
    }

    // 获取留言列表
    @RequestMapping(value = "/messages")
    public ResponseMessages getMessages(@RequestBody HashMap<String,String> data, HttpServletRequest request, HttpServletResponse response) {
        String ownerAccount = data.get("ownerAccount");
        System.out.println("收到message请求");
        /*if (ownerAccount == null){
            try {
                response.getWriter().print("please tell me the ownerAccount");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }*/
        // 获取当前登录的人
        String visitor = GeneralUtil.getCurrentUserFromCookie(request);{
            if (visitor.equals("please login first")){
                return null;
            }
        }
        System.out.println("留言板主人："+ownerAccount);
        System.out.println("访问者："+visitor);
        return generalService.getMessagePage(ownerAccount, visitor);
    }
}
