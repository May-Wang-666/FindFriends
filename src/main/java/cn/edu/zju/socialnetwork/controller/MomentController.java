package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.request.MomentInfo;
import cn.edu.zju.socialnetwork.response.ResponseMoments;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.ImageUtil;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/moment")
@CrossOrigin
public class MomentController {

    @Autowired
    private MomentService momentService;

    @Autowired
    Environment env;

    // 发表动态或者请求第n页动态
    @RequestMapping(value = "/refresh")
    public ResponseMoments momentRefresh(@RequestBody HashMap<String,String> data){
        return null;
    }

    // 发送动态
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String publish(@RequestBody MomentInfo info, HttpServletRequest request) {
        System.out.println("收到发表动态请求");
        String account = GeneralUtil.getCurrentUserFromCookie(request);
        if (account.equals("please login first")){
            return account;
        }
        String content = info.getContent();
        String picData = info.getPic();
        String time = info.getTime();
        String picurl = "";
        if (picData != null && !picData.equals("")) {
            picurl = ImageUtil.saveBase64Image(picData, env.getProperty("upload.path"));
        }
        if (time == null || time.equals("")){
            time = String.valueOf(System.currentTimeMillis());
        }
        return momentService.publishMoment(account, content, picurl, time);
    }

    // 删除动态
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody String id){
        return momentService.deleteMoment(Long.parseLong(id));
    }
}
