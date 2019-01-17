package cn.edu.zju.socialnetwork.request;

public class MessageInfo {

    // 被留言者账号
    private String toAccount;
    // 留言内容
    private String text;
    // 留言时间
    private String time;

    public MessageInfo(){}



    public String getToAccount() {
        return toAccount;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }
}
