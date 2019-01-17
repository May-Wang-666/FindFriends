package cn.edu.zju.socialnetwork.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseMessages {

    private List<MessageWithLike> messages;
    private int totalMessages;

    public ResponseMessages(List<MessageWithLike> messages, int totalMessages) {
        this.messages = messages;
        this.totalMessages = totalMessages;
    }

    public ResponseMessages(){
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }
}
