package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.util.GeneralUtil;
import cn.edu.zju.socialnetwork.util.StaticValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MomentServiceImp implements MomentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MomentRepository momentRepository;

    // 发表动态
    @Override
    public String publishMoment(String account, String content, String pic, String time) {
        User user = userRepository.findByEmail(account);
        if (user == null){
            return "invalid user";
        }
        Moment moment = new Moment(content,time,pic,user);
        momentRepository.save(moment);
        return "success";
    }

    // 删除动态
    @Override
    public String deleteMoment(Long id, HttpServletRequest request) {
        Moment moment = momentRepository.findMomentById(id);
        if (moment == null){
            return "no moment has id "+id;
        }  else {
            momentRepository.deleteById(id);
            String currentAccount = GeneralUtil.getCurrentUserFromCookie(request);
            // 返回用户动态数量
            int numOfMoments = momentRepository.findNumOfMoments(currentAccount);
            return String.valueOf(numOfMoments);
        }
    }

    // 获取某个人及其好友的某一页动态
    @Override
    public List<Moment> findMomentsOfMineAndFriends(String account, int pageNumber) {
        int from = StaticValues.numInOnePage * (pageNumber - 1);
        int to = StaticValues.numInOnePage * pageNumber;

        // 因findFriendsMoments接口问题，分两步获取
        User currentUser = userRepository.findByEmail(account);
        Set<User> users = currentUser.getFriends();
        List<String> emails=new ArrayList<>();
        emails.add(account);
        if (users != null && users.size() != 0){
            for (User user:users){
                emails.add(user.getEmail());
            }
        }
        return momentRepository.findMomentsByUsers(emails,from,to);
    }

    // 获取一个人的某一页动态
    @Override
    public List<Moment> findMyMoments(String account, int pageNumber) {
        int from = StaticValues.numInOnePage * (pageNumber - 1);
        System.out.println(from);
        int to = StaticValues.numInOnePage * pageNumber;
        return momentRepository.findMyMoments(account,from,to);
    }

    @Override
    public Moment findMomentById(Long id) {
        return momentRepository.findMomentById(id);
    }

    @Override
    public void saveMoment(Moment moment) {
        momentRepository.save(moment);
    }

    @Override
    public int findNumOfMyMoments(String account) {
        return momentRepository.findNumOfMoments(account);
    }

    @Override
    public int findNumOfFriendsMoments(String account) {
        User currentUser = userRepository.findByEmail(account);
        // 自己的动态条数
        int sum = momentRepository.findNumOfMoments(account);
        // 加上好友的动态条数
        Set<User> users = currentUser.getFriends();
        if (users != null && users.size() != 0){
            for (User user:users){
                sum += momentRepository.findNumOfMoments(user.getEmail());
            }
        }
        return sum;
    }
}
