package cn.edu.zju.socialnetwork.service;


import cn.edu.zju.socialnetwork.response.HomePageInfo;

public interface GeneralService {

    /**
     * 用户对动态或留言进行点赞
     * @param userAccount 点赞用户
     * @param itemId      点赞对象的id
     * @param type        moment / message
     * @return true
     */
    Boolean like(String userAccount, Long itemId, String type);

    // 取消点赞
    Boolean cancelLike(String userAccount, Long itemId, String type);

    // 获取主页信息
    HomePageInfo getHomePage(String ownerAccount,String visitorAccount);

}
