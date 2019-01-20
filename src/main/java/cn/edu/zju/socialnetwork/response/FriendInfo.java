package cn.edu.zju.socialnetwork.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendInfo {

    private String email;
    private String name;
    private String headpic;

    // 是否已经是你的好友
    private boolean isFriends;

    public FriendInfo(String email, String name, String headpic) {
        this.email = email;
        this.name = name;
        this.headpic = headpic;
    }

    public FriendInfo() {
    }

    public void setFriends(boolean friends) {
        isFriends = friends;
    }
}
