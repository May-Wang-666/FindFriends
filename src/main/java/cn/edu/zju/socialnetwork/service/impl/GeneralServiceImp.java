package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.response.AdditionalMessage;
import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.AdditionalMoment;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.MessageService;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.service.UserService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GeneralServiceImp implements GeneralService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    MomentService momentService;

    @Autowired
    MessageService messageService;

    /**
     * 给动态/留言点赞
     *
     * @param userAccount 点赞用户
     * @param itemId      点赞对象的id
     * @param type        moment / message
     * @return id不存在，false，否则，true
     */
    @Override
    public Boolean like(String userAccount, Long itemId, String type) {
        User currentUser = userService.findByAccount(userAccount);
        if (type.equals("moment")) {
            Moment moment = momentService.findMomentById(itemId);
            if (moment == null) {
                System.out.println("id 为 " + itemId + "的 " + type + " 不存在");
                return false;
            }
            moment.likedByUser(currentUser);
            momentService.saveMoment(moment);
        } else if (type.equals("message")) {
            Message message = messageService.findMessageById(itemId);
            if (message == null) {
                System.out.println("id 为 " + itemId + "的 " + type + " 不存在");
                return false;
            }
            message.likedByUser(currentUser);
            messageService.saveMessage(message);
        }
        return true;
    }

    // 取消动态/留言点赞
    @Override
    public Boolean cancelLike(String userAccount, Long itemId, String type) {
        if (type.equals("moment")) {
            Moment moment = momentService.findMomentById(itemId);
            if (moment == null) {
                System.out.println("id 为 " + itemId + "的 " + type + " 不存在");
                return false;
            }
            moment.cancledLikeBy(userAccount);
            momentService.saveMoment(moment);
        } else if (type.equals("message")) {
            Message message = messageService.findMessageById(itemId);
            if (message == null) {
                System.out.println("id 为 " + itemId + "的 " + type + " 不存在");
                return false;
            }
            message.cancledLikeBy(userAccount);
            messageService.saveMessage(message);
        }
        return true;
    }

    /**
     * 获取用户主页信息
     * 包括个人信息、统计信息、好友列表和个人动态列表
     *
     * @param ownerAccount   主页拥有者
     * @param visitorAccount 主页访问者，可以是拥有者本身
     * @return
     */
    @Override
    public HomePageInfo getHomePage(String ownerAccount, String visitorAccount) {
        User owner = userService.findByAccount(ownerAccount);
        User visitor = userService.findByAccount(visitorAccount);
        // 用户的好友列表
        Set<User> friends;
        if (owner.getFriends() == null) {
            friends = new HashSet<>();
        } else {
            friends = owner.getFriends();
        }
        // 用户留言数
        int numOfMessages = userRepository.findNumOfMessages(ownerAccount);
        // 用户本人动态数
        int numOfMoments = userRepository.findNumOfMoments(ownerAccount);
        // 主页的动态列表
        List<Moment> moments;
        // 如果是用户本人访问本人的主页，可以看到所有好友动态
        if (ownerAccount.equals(visitorAccount)) {
            moments = momentService.findMomentsOfMineAndFriends(ownerAccount, 1);
        } else {
            moments = momentService.findMyMoments(ownerAccount, 1);
        }
        // 判断访问者是否对显示的每条动态点赞，访问者可以是owner本身
        List<AdditionalMoment> additionalMoments = GeneralUtil.addInfoIntoMoments(moments, visitor);
        HomePageInfo info = new HomePageInfo(owner, new ArrayList<>(friends), numOfMoments, numOfMessages, additionalMoments);
        // 判断是否最后一页
        if (additionalMoments.size() <= 10) {
            info.setLastPage(true);
        }
        return info;
    }


    /**
     * 获取用户留言板信息
     *
     * @param ownerAccount   留言板主人账号
     * @param visitorAccount 留言板访问者账号
     * @return 有留言，List<AdditionalMessage> 没有留言，size为0的list
     */
    @Override
    public ResponseMessages getMessagePage(String ownerAccount, String visitorAccount, int pageNumber) {
        User owner = userService.findByAccount(ownerAccount);
        User visitor = userService.findByAccount(visitorAccount);
        List<Message> messages = messageService.findMessagesByAccount(ownerAccount, pageNumber);
        int totalMessage = messageService.findTotalMessageByAccount(ownerAccount);
        System.out.println("留言数：" + totalMessage);
        List<AdditionalMessage> additionalMessages = GeneralUtil.addInfoIntoMessages(messages, owner, visitor);
        return new ResponseMessages(owner.getName(), owner.getHeadpic(), additionalMessages, totalMessage);
    }
}
