package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Moment {

    @Id
    @GeneratedValue
    private Long id;

    // 头图
    private String headpic;
    // 内容
    private String content;
    // 发表时间
    private String time;
    // 内容图片
    private String pic;

    @Relationship(type = "blongs_to",direction = Relationship.OUTGOING)
    User owner;

    public Moment(Long id, String headpic, String content, String time, String pic) {
        this.id = id;
        this.headpic = headpic;
        this.content = content;
        this.time = time;
        this.pic = pic;
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

    public String getHeadpic() {
        return headpic;
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

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
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
}
