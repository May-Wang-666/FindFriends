package cn.edu.zju.socialnetwork.request;

public class MomentInfo {

    // 内容
    private String content;
    // 发表时间
    private String time;
    // 内容图片
    private String pic;

    public MomentInfo(){}

    public MomentInfo(String content, String time, String pic) {
        this.content = content;
        this.time = time;
        this.pic = pic;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public String getPic() {
        return pic;
    }
}
