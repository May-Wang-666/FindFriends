package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.MessageService;
import cn.edu.zju.socialnetwork.util.StaticStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 向数据库中写入留言
     *
     * @param fromAccount 留言者
     * @param toAccount   被留言者
     * @param text        留言内容
     * @param time        留言时间
     * @return success
     */
    @Override
    public String leaveMessage(String fromAccount, String toAccount, String text, String time) {
        User from = userRepository.findByEmail(fromAccount);
        User to = userRepository.findByEmail(toAccount);
        if (from == null) {
            return "invalid fromAccout";
        }
        if (to == null) {
            return "invalid toAccount";
        }
        Message newMessage = new Message(text, time, from);
        to.addMessage(newMessage);
        messageRepository.save(newMessage);
        userRepository.save(to);
        return "success";
    }

    // 根据留言id删除留言
    @Override
    public String deleteMessage(Long id) {
        Message message = messageRepository.findMessageById(id);
        if (message == null) {
            return "no message has id " + id;
        } else {
            messageRepository.deleteById(id);
            return "success";
        }
    }

    @Override
    public List<Message> findMessagesByAccount(String account, int pageNumber) {
        int form = StaticStrings.numInOnePage * (pageNumber-1);
        int to = StaticStrings.numInOnePage * pageNumber;
        return messageRepository.findMessagesByAccount(account,form,to);
    }
}
