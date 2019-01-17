package cn.edu.zju.socialnetwork.service;


public interface MessageService {

    // 添加留言
    String leaveMessage(String fromAccount, String toAccount, String text, String time);

    // 删除留言
    String deleteMessage(Long id);

}
