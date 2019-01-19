package cn.edu.zju.socialnetwork.service;


import cn.edu.zju.socialnetwork.entity.Message;

import java.util.List;

public interface MessageService {

    // 添加留言
    String leaveMessage(String fromAccount, String toAccount, String text, String time);

    // 删除留言
    String deleteMessage(Long id);

    // 获取某人的留言
    List<Message> findMessagesByAccount(String account,int pageNumber);

}
