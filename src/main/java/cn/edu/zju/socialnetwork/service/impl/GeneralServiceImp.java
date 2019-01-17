package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.MomentWithLike;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GeneralServiceImp implements GeneralService {

    @Autowired
    UserRepository userRepository;

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
        System.out.println(owner);
        User visitor = userRepository.findByEmail(visitorAccount);
        // 用户的好友列表
        Set<User> friends = owner.getFriends();
        // 用户留言数
        int numOfMessages = userRepository.findNumOfMessages(ownerAccount);
        // 用户本人动态数
        int numOfMoments = userRepository.findNumOfMoments(ownerAccount);
        // 主页的动态列表
        List<Moment> moments;
        // 如果是用户本人访问本人的主页，可以看到所有好友动态
        if (ownerAccount.equals(visitorAccount)) {
            moments = userRepository.findFriendsMoments(ownerAccount,1);
        } else {
            moments = userRepository.findMyMoments(ownerAccount,1);
        }
        // 判断访问者是否对显示的每条动态点赞，访问者可以是owner本身
        List<MomentWithLike> momentWithLikes = new ArrayList<>();
        if(moments != null && moments.size() != 0){
            for (Moment moment : moments) {
                MomentWithLike rm = new MomentWithLike(moment.getId(), moment.getOwner().getName(), moment.getOwner().getHeadpic(), moment.getContent(), moment.getTime(), moment.getPic());
                Set<User> likedUsers = moment.getLikedBy();
                if (likedUsers != null && likedUsers.size() != 0) {
                    rm.setLike(likedUsers.size());
                    if (GeneralUtil.isIn(likedUsers, visitor)){
                        rm.setLiked(true);
                    }
                }
                momentWithLikes.add(rm);
            }
        }

        HomePageInfo info = new HomePageInfo(owner, new ArrayList<>(friends), numOfMoments, numOfMessages, momentWithLikes);
        // 判断是否最后一页
        if (momentWithLikes.size() <= 10){
            info.setLastPageOfMoment(true);
        }
        return info;
    }
}
