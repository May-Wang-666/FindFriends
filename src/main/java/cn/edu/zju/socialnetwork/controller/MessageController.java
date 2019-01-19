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
            String toAccount = data.get("toAccount");
            String text = data.get("text");
            String time = String.valueOf(System.currentTimeMillis());
            String fromAccount = GeneralUtil.getCurrentUserFromCookie(request);
            messageService.leaveMessage(fromAccount, toAccount, text, time);
            return generalService.getMessagePage(toAccount, fromAccount);
           /*
           List<Message> newMessages = messageService.findMessagesByAccount(toAccount,1);
           User owner = userService.findByAccount(toAccount);
            User visitor = userService.findByAccount(fromAccount);
            return GeneralUtil.addInfoIntoMessages(newMessages,owner,visitor);*/
        }
        return null;
    }


    // 删除留言
    @RequestMapping(value = "/delete")
    public String delete(@RequestBody String id) {
        return messageService.deleteMessage(Long.parseLong(id));
    }

}
