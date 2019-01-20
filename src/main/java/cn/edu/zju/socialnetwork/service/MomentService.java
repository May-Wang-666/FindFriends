package cn.edu.zju.socialnetwork.service;

import cn.edu.zju.socialnetwork.entity.Moment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MomentService {

    // 发表动态
    String publishMoment(String account,String content,String pic,String time);

    // 删除动态
    String deleteMoment(Long id,HttpServletRequest request);

    // 获取某人及其好友的当前页动态
    List<Moment> findMomentsOfMineAndFriends(String account,int pageNumber);

    // 获取某人的当前页动态
    List<Moment> findMyMoments(String account,int pageNumber);

    // 根据id获取动态
    Moment findMomentById(Long id);

    // 保存动态
    void saveMoment(Moment moment);

}
