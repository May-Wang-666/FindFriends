package cn.edu.zju.socialnetwork.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 包含了当前用户是否点赞的动态信息
public class AdditionalMoment {

    private Long id;
    // 用户昵称
    private String nickname;
    // 用户头像
    private String headpic;
    // 动态内容
    private String text;
    // 发表时间
    private String time;
    // 点赞人数
    private int like;
    // 当前访问用户是否点赞
    private boolean liked;
    // 当前用户是否可以删除
    private boolean isDeletable;
    // 动态图片
    private String pic;

    public AdditionalMoment(){}
    public AdditionalMoment(Long id, String nickname, String headpic, String text, String time, String pic) {
        this.id = id;
        this.nickname = nickname;
        this.headpic = headpic;
        this.text = text;
        this.time = time;
        this.pic = pic;
        this.liked = false;
        this.isDeletable = false;
        this.like = 0;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }
}