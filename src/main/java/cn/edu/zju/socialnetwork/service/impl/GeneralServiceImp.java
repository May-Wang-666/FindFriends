package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.MessageWithLike;
import cn.edu.zju.socialnetwork.response.MomentWithLike;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
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
    MomentRepository momentRepository;

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Boolean like(String userAccount, Long itemId, String type) {
        return null;
    }

    @Override
    public Boolean cancelLike(String userAccount, Long itemId, String type) {
        return null;
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
        User owner = userRepository.findByEmail(ownerAccount);
        User visitor = userRepository.findByEmail(visitorAccount);
        // 用户的好友列表
        Set<User> friends = owner.getFriends();
        if (friends == null){
            friends = new HashSet<>();
        }
        // 用户留言数
        int numOfMessages = userRepository.findNumOfMessages(ownerAccount);
        // 用户本人动态数
        int numOfMoments = userRepository.findNumOfMoments(ownerAccount);
        // 主页的动态列表
        List<Moment> moments;
        // 如果是用户本人访问本人的主页，可以看到所有好友动态
        if (ownerAccount.equals(visitorAccount)) {
            moments = momentRepository.findFriendsMoments(ownerAccount,1);
        } else {
            moments = userRepository.findMyMoments(ownerAccount,1);
            // 另一种方案
           // moments = momentRepository.findAllByOwnerEmail(ownerAccount);
        }
        // 判断访问者是否对显示的每条动态点赞，访问者可以是owner本身
        List<MomentWithLike> momentWithLikes = GeneralUtil.addLikeInfoIntoMoments(moments,visitor);
        HomePageInfo info = new HomePageInfo(owner, new ArrayList<>(friends), numOfMoments, numOfMessages, momentWithLikes);
        // 判断是否最后一页
        if (momentWithLikes.size() <= 10){
            info.setLastPageOfMoment(true);
        }
        System.out.println(info);
        return info;
    }


    /**
     * 获取用户留言板信息
     *
     * @param ownerAccount   留言板主人账号
     * @param visitorAccount 留言板访问者账号
     * @return 有留言，List<MessageWithLike> 没有留言，size为0的list
     */
    @Override
    public ResponseMessages getMessagePage(String ownerAccount, String visitorAccount) {
        User owner = userRepository.findByEmail(ownerAccount);
        User visitor = userRepository.findByEmail(visitorAccount);
        List<Message> messages = messageRepository.findMessagesByAccount(ownerAccount);
        int totalMessage = messages.size();
        System.out.println("留言数：" + totalMessage);
        List<MessageWithLike> messageWithLikes = new ArrayList<>();
        if (messages.size() != 0) {
            for (Message m : messages) {
                User messageOwner = m.getOwner();
                MessageWithLike rm = new MessageWithLike(m.getId(), messageOwner.getName(), messageOwner.getHeadpic(), m.getText(), m.getTime());
                Set<User> likedUsers = m.getLikedBy();
                if (likedUsers != null && likedUsers.size() != 0) {
                    rm.setLike(likedUsers.size());
                    rm.setLiked(GeneralUtil.isIn(likedUsers, visitor));
                }
                messageWithLikes.add(rm);
            }
        }
        return new ResponseMessages(owner.getName(),owner.getHeadpic(),messageWithLikes,totalMessage);
    }
}
