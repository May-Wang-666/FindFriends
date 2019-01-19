package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@NodeEntity
public class Message {

    @Id
    @GeneratedValue
    private Long id;

    // 留言内容
    private String text;
    // 留言时间
    private String time;

    // 留言者
    @Relationship(type = "leaves", direction = Relationship.INCOMING)
    private User owner;

    // 喜欢这个留言的人
    @Relationship(type = "liked", direction = Relationship.INCOMING)
    private Set<User> likedBy;


    public Message() {
    }

    public Message(String text, String time, User owner) {
        this.text = text;
        this.time = time;
        this.owner = owner;
    }


    // 用户点赞
    public void likedByUser(User user) {
        if (likedBy == null) {
            likedBy = new HashSet<>();
        }
        likedBy.add(user);
    }

    // 用户取消点赞
    public void cancledLikeBy(String userAccount) {
        Iterator<User> iterator = likedBy.iterator();
        while (iterator.hasNext()) {
            User tmp = iterator.next();
            if (tmp.getEmail().equals(userAccount)) {
                iterator.remove();
            }
        }
    }

    public int getLikeCount(){
        if (likedBy != null){
            return likedBy.size();
        }
        else return 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public User getOwner() {
        return owner;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", owner=" + owner +
                ", likedBy=" + likedBy +
                '}';
    }
}
