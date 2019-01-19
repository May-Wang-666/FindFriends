package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.request.MomentInfo;
import cn.edu.zju.socialnetwork.response.MomentWithLike;
import cn.edu.zju.socialnetwork.response.ResponseMoments;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/moment")
public class MomentController {

    @Autowired
    private MomentService momentService;

    @Autowired
    private UserService userService;

    @Autowired
    Environment env;

    // 发表动态或者请求第n页动态
    @RequestMapping(value = "/refresh")
    public ResponseMoments momentRefresh(@RequestBody HashMap<String,String> data, HttpServletRequest request, HttpServletResponse response){
        // 发表动态
        System.out.println(request.getMethod());
        System.out.println(data);
        if (request.getMethod().equals(String.valueOf(RequestMethod.POST))){
            System.out.println("收到发表动态请求：");
            String account = GeneralUtil.getCurrentUserFromCookie(request);
            User currentUser = userService.findByAccount(account);
            String content = data.get("content");
            String pic = data.get("pic");
            publishMoment(account,content,pic);
            List<Moment> newMoments = momentService.findMomentsOfMineAndFriends(account,1);
            List<MomentWithLike> res = GeneralUtil.addLikeInfoIntoMoments(newMoments,currentUser);
            if (newMoments.size() > 10){
                return new ResponseMoments(res,false);
            } else {
                return new ResponseMoments(res,true);
            }
        }
        // 请求分页动态
        if (request.getMethod().equals(String.valueOf(RequestMethod.GET))){
            System.out.println("收到动态分页请求：");
            String pageNumber = data.get("pageNumber");
            String ownerAccount = data.get("ownerAccount");
            String visitorAccount = GeneralUtil.getCurrentUserFromCookie(request);
        }
        return null;
    }



    // 发表动态
    private String publishMoment( String account,String content,String picData) {
        String time = String.valueOf(System.currentTimeMillis());
        String picurl = "";
        if (picData != null && !picData.equals("")) {
            picurl = ImageUtil.saveBase64Image(picData, env.getProperty("upload.path"));
        }
        return momentService.publishMoment(account, content, picurl, time);
    }

    // 删除动态
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody String id){
        return momentService.deleteMoment(Long.parseLong(id));
    }
}
