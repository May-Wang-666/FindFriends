package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.MessageService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.StaticValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
        newMessage.setOwner(from);
        to.addMessage(newMessage);
        messageRepository.save(newMessage);
        userRepository.save(to);
        return "success";
    }

    // 根据留言id删除留言
    @Override
    public String deleteMessage(Long id, HttpServletRequest request) {
        Message message = messageRepository.findMessageById(id);
        if (message == null) {
            return "no message has id " + id;
        } else {
            messageRepository.deleteById(id);
            String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
            int numOfMessages = messageRepository.findTotalMessageByEmail(currentAccount);
            return String.valueOf(numOfMessages);
        }
    }

    @Override
    public List<Message> findMessagesByAccount(String account, int pageNumber) {
        int form = StaticValues.numInOnePage * (pageNumber-1);
        int to = StaticValues.numInOnePage * pageNumber;
        return messageRepository.findMessagesByAccount(account,form,to);
    }

    @Override
    public int findTotalMessageByAccount(String account) {
        return messageRepository.findTotalMessageByEmail(account);
    }

    @Override
    public Message findMessageById(Long id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
}
