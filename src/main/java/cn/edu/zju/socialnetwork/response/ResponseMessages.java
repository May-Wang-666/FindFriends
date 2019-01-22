package cn.edu.zju.socialnetwork.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMessages {

    private UserInfo ownerInfo;
    private List<AdditionalMessage> messages;
    private int totalMessages;

    public ResponseMessages(String name, String headpic, List<AdditionalMessage> messages, int totalMessages) {
        System.out.println("用户名："+name);
        System.out.println("头像："+headpic);
        ownerInfo = new UserInfo(name, headpic);
        this.messages = messages;
        this.totalMessages = totalMessages;
    }

    public ResponseMessages() {
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    @Data
    private static class UserInfo {
        private String name;
        private String headpic;

        public UserInfo() {
        }

        public UserInfo(String name, String headpic) {
            this.name = name;
            this.headpic = headpic;
        }
    }
}
