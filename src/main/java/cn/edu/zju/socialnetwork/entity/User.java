package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.*;

@NodeEntity
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String account;
    private String nickname;
    private String password;
    private String phonenumber;
    private String school;

    public User() {
    }

    public User(String name, String account, String nickname, String password, String phonenumber, String school) {
        this.name = name;
        this.account = account;
        this.nickname = nickname;
        this.password = password;
        this.phonenumber = phonenumber;
        this.school = school;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccount() {
        return account;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getSchool() {
        return school;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
