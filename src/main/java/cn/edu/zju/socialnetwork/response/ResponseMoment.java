package cn.edu.zju.socialnetwork.response;


// 包含了当前用户是否点赞的动态信息
public class ResponseMoment{
    private String nickname;
    private String headpic;
    private String text;
    private String time;
    private boolean like;
    private String pic;

    public ResponseMoment(){}
    public ResponseMoment(String nickname, String headpic, String text, String time, boolean like, String pic) {
        this.nickname = nickname;
        this.headpic = headpic;
        this.text = text;
        this.time = time;
        this.like = like;
        this.pic = pic;
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

    public void setLike(boolean like) {
        this.like = like;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}