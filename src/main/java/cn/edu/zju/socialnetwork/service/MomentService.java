package cn.edu.zju.socialnetwork.service;

public interface MomentService {

    // 发表动态
    String publishMoment(String account,String content,String pic,String time);

    // 删除动态
    String deleteMoment(Long id);
}
