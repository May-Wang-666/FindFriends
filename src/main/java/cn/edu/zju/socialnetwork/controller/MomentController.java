package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.response.AdditionalMoment;
import cn.edu.zju.socialnetwork.response.ResponseMoments;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import cn.edu.zju.socialnetwork.util.StaticValues;
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
        System.out.println(data);
        String isPublish = data.get("isPublish");
        if (isPublish.equals("true")){
            System.out.println("收到发表动态请求：");
            String account = GeneralUtil.getCurrentUserFromCookie(request);
            User currentUser = userService.findByAccount(account);
            String content = data.get("content");
            String pic = data.get("pic");
            publishMoment(account,content,pic);
            List<Moment> newMoments = momentService.findMomentsOfMineAndFriends(account,1);
            List<AdditionalMoment> res = GeneralUtil.addInfoIntoMoments(newMoments,currentUser);
            if (newMoments.size() < StaticValues.numInOnePage){
                return new ResponseMoments(res,false);
            } else {
                return new ResponseMoments(res,true);
            }
        }
        // 请求分页动态
        if (isPublish.equals("false")){
            System.out.println("收到动态分页请求：");
            String pageNumber = data.get("pageNumber");
            String ownerAccount = data.get("ownerAccount");
            String visitorAccount = GeneralUtil.getCurrentUserFromCookie(request);
            List<Moment> newMoments;
            if(ownerAccount.equals(visitorAccount)){
                newMoments = momentService.findMomentsOfMineAndFriends(ownerAccount,Integer.valueOf(pageNumber));
            } else {
                newMoments = momentService.findMyMoments(ownerAccount,Integer.valueOf(pageNumber));
            }
            User visitor = userService.findByAccount(visitorAccount);
            List<AdditionalMoment> res = GeneralUtil.addInfoIntoMoments(newMoments,visitor);
            if (newMoments.size() < StaticValues.numInOnePage){
                return new ResponseMoments(res,false);
            } else {
                return new ResponseMoments(res,true);
            }
        }
        return null;
    }


    // 删除动态
    // 返回留言时已经给出是否可以删除字段
    // 删除权限由前端控制
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody HashMap<String,String> data, HttpServletRequest request){
        String id = data.get("id");
        return momentService.deleteMoment(Long.parseLong(id.trim()),request);
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

}
