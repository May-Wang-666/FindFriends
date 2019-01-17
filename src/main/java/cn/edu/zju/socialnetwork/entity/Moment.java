package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@NodeEntity
public class Moment {

    @Id
    @GeneratedValue
    private Long id;

    // 内容
    private String content;
    // 发表时间
    private String time;
    // 内容图片
    private String pic;

    @Relationship(type = "belongs_to", direction = Relationship.OUTGOING)
    private User owner;

    @Relationship(type = "liked", direction = Relationship.INCOMING)
    private Set<User> likedBy;

    public Moment() {
    }

    public Moment(String content, String time, String pic) {
        this.content = content;
        this.time = time;
        this.pic = pic;
    }

    public Moment(String content, String time, String pic, User owner) {
        this.content = content;
        this.time = time;
        this.pic = pic;
        this.owner = owner;
    }

    // 用户点赞
    public void likedByUser(User user) {
        if (likedBy == null) {
            likedBy = new HashSet<>();
        }
        likedBy.add(user);
    }

    // 取消点赞，从点赞集合中删除该用户
    public void cancledLikeBy(String userAccount) {
        Iterator<User> iterator = likedBy.iterator();
        while (iterator.hasNext()) {
            User tmp = iterator.next();
            if (tmp.getEmail().equals(userAccount)) {
                iterator.remove();
            }
        }
    }

    public int getLikeCount() {
        if (likedBy != null) {
            return likedBy.size();
        } else {
            return 0;
        }
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }

    public Moment(Long id, String content, String time, String pic, User owner, Set<User> likedBy) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.pic = pic;
        this.owner = owner;
        this.likedBy = likedBy;
    }

    @Override
    public String toString() {
        return "Moment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", pic='" + pic + '\'' +
                ", owner=" + owner +
                ", likedBy=" + likedBy +
                '}';
    }
}
