package cn.edu.zju.socialnetwork.request;

public class RegisterUserInfo {

    // 邮箱
    private String email;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 个性签名
    private String motto;
    // 性别
    private String sex;
    //年龄
    private int age;
    // 星座
    private String xinzuo;


    public RegisterUserInfo(String email, String password, String nickname, String motto, String sex, int age, String xinzuo) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.motto = motto;
        this.sex = sex;
        this.age = age;
        this.xinzuo = xinzuo;
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

    @Override
    public String toString() {
        return "RegisterUserInfo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", motto='" + motto + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", xinzuo='" + xinzuo + '\'' +
                '}';
    }
}
