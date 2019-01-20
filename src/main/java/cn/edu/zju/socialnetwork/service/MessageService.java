package cn.edu.zju.socialnetwork.service;


import cn.edu.zju.socialnetwork.entity.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MessageService {

    // 添加留言
    String leaveMessage(String fromAccount, String toAccount, String text, String time);

    // 删除留言
    String deleteMessage(Long id, HttpServletRequest request);

    // 获取某人的留言
    List<Message> findMessagesByAccount(String account,int pageNumber);

    // 获取某人留言数
    int findTotalMessageByAccount(String account);

}
