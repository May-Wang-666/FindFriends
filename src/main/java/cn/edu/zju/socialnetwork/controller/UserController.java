package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.request.RegisterUserInfo;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    Environment env;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String say() {
        return "Hello SpringBoot!!!";
    }

    // 测试上传文件接口
    @RequestMapping(value = "/test" ,method = RequestMethod.POST)
    public String testUploadingImg(@RequestBody String dataURL){
        System.out.println("传入数据：");
        System.out.println(dataURL);
        String localURL = ImageUtil.saveBase64Image(dataURL,env.getProperty("upload.path"));
        System.out.println("成功保存图片："+localURL);
        return localURL;
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody RegisterUserInfo userInfo){
        System.out.println(userInfo);
        String res = userService.register(userInfo);
        System.out.println(res);
        if (res.equals("invalid email")){
            return "email address already used";
        }
        if (res.equals("internal error")){
            return "sorry, server sucks";
        }
        return "success";
    }
}
