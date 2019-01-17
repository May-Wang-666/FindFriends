package cn.edu.zju.socialnetwork.entity;

import org.neo4j.ogm.annotation.*;

import java.util.*;

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
    private String name;
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

    @Relationship(value = "is_friends_with", type = Relationship.UNDIRECTED)
    private Set<User> friends;

    // 收到的留言
    @Relationship(value = "have", type = Relationship.INCOMING)
    private List<Message> messages;


    public User() {
    }

    public User(String email, String password, String name, String headpic, String motto, String sex, int age, String xinzuo) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.headpic = headpic;
        this.motto = motto;
        this.sex = sex;
        this.age = age;
        this.xinzuo = xinzuo;
        friends = new HashSet<>();
    }

    public User(Long id, String email, String password, String name, String headpic, String motto, String sex, int age, String xinzuo, Set<User> friends) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.headpic = headpic;
        this.motto = motto;
        this.sex = sex;
        this.age = age;
        this.xinzuo = xinzuo;
        this.friends = friends;
    }

    // 相互加好友
    public void makeFriendsWith(User user) {
        friends.add(user);
        user.getFriends().add(this);
    }

    // 获得一个留言
    public void addMessage(Message message){
        if (messages == null){
            messages = new ArrayList<>();
        }
        messages.add(message);
    }

    // 删除一条留言
    public void removeMessage(Message message){
        Iterator<Message> iterator = messages.iterator();
        while (iterator.hasNext()){
            Message tmp = iterator.next();
            if (tmp.getId() == message.getId()){
                iterator.remove();
                break;
            }
        }
        System.out.println("找不到id为 "+message.getId()+" 的留言");
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
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

    public String getName() {
        return name;
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

    public Set<User> getFriends() {
        return friends;
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

    public void setName(String name) {
        this.name = name;
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

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", headpic='" + headpic + '\'' +
                ", motto='" + motto + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", xinzuo='" + xinzuo + '\'' +
                ", friends=" + friends +
                '}';
    }
}
