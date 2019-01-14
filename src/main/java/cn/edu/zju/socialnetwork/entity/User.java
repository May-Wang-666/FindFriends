package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.*;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    // 邮箱
    private String email;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 头像
    private String headpic;
    // 个性签名
    private String motto;
    // 性别
    private String sex;
    //年龄
    private int age;
    // 星座
    private String xinzuo;


    public User() {
    }

    public User( String email, String password, String nickname, String headpic, String motto, String sex, int age, String xinzuo) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.headpic = headpic;
        this.motto = motto;
        this.sex = sex;
        this.age = age;
        this.xinzuo = xinzuo;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHeadpic() {
        return headpic;
    }

    public String getMotto() {
        return motto;
    }

    public String getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public String getXinzuo() {
        return xinzuo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setXinzuo(String xinzuo) {
        this.xinzuo = xinzuo;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headpic='" + headpic + '\'' +
                ", motto='" + motto + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", xinzuo='" + xinzuo + '\'' +
                '}';
    }
}
