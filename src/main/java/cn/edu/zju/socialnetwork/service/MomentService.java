package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.Moment;

import java.util.List;

public interface MomentService {

    // 发表动态
    String publishMoment(String account,String content,String pic,String time);

    // 删除动态
    String deleteMoment(Long id);

    // 获取某人及其好友的所有动态
    List<Moment> findMomentsOfMineAndFriends(String account,int pageNumber);

    // 获取某人的所有动态
    List<Moment> findMyMoments(String account,int pageNumber);
}
