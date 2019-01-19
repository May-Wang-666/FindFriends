package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.request.MessageInfo;
import cn.edu.zju.socialnetwork.response.ResponseMoments;
import cn.edu.zju.socialnetwork.service.MessageService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    // 发表留言或者请求第n页留言
    @RequestMapping(value = "/refresh")
    public ResponseMoments momentRefresh(@RequestBody HashMap<String,String> data){
        return null;
    }

    // 发表留言
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public String publish(@RequestBody MessageInfo info, HttpServletRequest request){
        // 获取当前登录状态
        String fromAccount = GeneralUtil.getCurrentUserFromCookie(request);
        if (fromAccount.equals("no corresponding cookie")){
            return "please login first";
        }
        String toAccount = info.getToAccount();
        String text = info.getText();
        String time = info.getTime();
        if (time == null || time.equals("")){
            time = String.valueOf(System.currentTimeMillis());
        }
        return messageService.leaveMessage(fromAccount,toAccount,text,time);
    }

    // 删除留言
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody String id){
        return messageService.deleteMessage(Long.parseLong(id));
    }

}
