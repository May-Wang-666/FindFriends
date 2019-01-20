package cn.edu.zju.socialnetwork.controller;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.response.AdditionalMessage;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.MessageService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    GeneralService generalService;

    @Autowired
    UserService userService;

    // 发表留言或者请求第n页留言
    @RequestMapping(value = "/refresh")
    public ResponseMessages momentRefresh(@RequestBody HashMap<String, String> data, HttpServletRequest request) {
        String reqMethod = request.getMethod();
        // 发表留言
        if (reqMethod.equals(String.valueOf(RequestMethod.POST))) {
            System.out.println("收到发表留言请求：");
            String toAccount = data.get("toAccount");
            String text = data.get("text");
            System.out.println("toAccount"+toAccount);
            System.out.println("text: "+text);
            String time = String.valueOf(System.currentTimeMillis());
            String fromAccount = GeneralUtil.getCurrentUserFromCookie(request);
            messageService.leaveMessage(fromAccount, toAccount, text, time);
            return generalService.getMessagePage(toAccount, fromAccount,1);
        }
        // 分页留言
        if (reqMethod.equals(String.valueOf(RequestMethod.GET))){
            System.out.println("收到分页留言请求：");
            String ownerAccount = data.get("ownerAccount");
            String pageNumber = data.get("pageNumber");
            String visitorAccount = GeneralUtil.getCurrentUserFromCookie(request);
            return generalService.getMessagePage(ownerAccount,visitorAccount,Integer.valueOf(pageNumber));
        }
        return null;
    }


    // 删除留言
    // 返回留言时已经给出是否可以删除字段
    // 删除权限由前端控制
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody HashMap<String,String> data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = data.get("id");
        if (id == null || id.equals("")){
            response.sendError(488,"received id is null");
            return null;
        }
        String totalMessages = messageService.deleteMessage(Long.parseLong(id.trim()),request);
        return totalMessages;
    }

}
