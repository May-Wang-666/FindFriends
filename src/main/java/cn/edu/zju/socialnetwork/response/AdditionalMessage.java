package cn.edu.zju.socialnetwork.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalMessage {

    private Long id;
    // 留言者昵称
    private String nick;
    // 留言者头像
    private String headpic;
    // 留言内容
    private String text;
    // 留言时间
    private String time;
    // 点赞人数
    private int like;
    // 当前访问者是否点赞
    private boolean liked;
    // 当前访问者是否可以删除
    private boolean isDeletable;

    public AdditionalMessage() {
    }

    public AdditionalMessage(Long id, String nick, String headpic, String text, String time) {
        this.id = id;
        this.nick = nick;
        this.headpic = headpic;
        this.text = text;
        this.time = time;
        this.liked = false;
        this.isDeletable = false;
        this.like = 0;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }
}
