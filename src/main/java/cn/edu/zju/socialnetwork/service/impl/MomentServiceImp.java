package cn.edu.zju.socialnetwork.service.impl;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.MomentService;
import cn.edu.zju.socialnetwork.util.StaticStrings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String deleteMoment(Long id) {
        Moment moment = momentRepository.findMomentById(id);
        if (moment == null){
            return "no moment has id "+id;
        } else {
            momentRepository.deleteById(id);
            return "success";
        }
    }

    // 获取某个人及其好友的某一页动态
    @Override
    public List<Moment> findMomentsOfMineAndFriends(String account, int pageNumber) {
        int from = StaticStrings.numInOnePage * (pageNumber - 1);
        int to = StaticStrings.numInOnePage * pageNumber;
        return momentRepository.findFriendsMoments(account,from,to);
    }

    // 获取一个人的某一页动态
    @Override
    public List<Moment> findMyMoments(String account, int pageNumber) {
        int from = StaticStrings.numInOnePage * (pageNumber - 1);
        int to = StaticStrings.numInOnePage * pageNumber;
        return momentRepository.findMyMoments(account,from,to);
    }
}
