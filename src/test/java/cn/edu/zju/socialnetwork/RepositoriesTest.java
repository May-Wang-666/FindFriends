package cn.edu.zju.socialnetwork;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.request.LoginInfo;
import cn.edu.zju.socialnetwork.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RepositoriesTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MomentRepository momentRepository;

    @Autowired
    MessageRepository messageRepository;

    private String defaultPic;
    private String[] randomPics = {"1.jpg","2.jpg","3.jpg","4.jpg"};
    private String localAddress;

    @Override
    public void init() {

        try {
            //localAddress = InetAddress.getLocalHost().getHostAddress();
            localAddress = "47.100.226.85";
            defaultPic = "http://" + localAddress + ":8080/find-friends/headpics/default_headpic.png";
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.init();
    }

    private String getRandomHeadPic(){
        Random random = new Random();
        int index = random.nextInt(4);
        return "http://"+localAddress+":8080/find-friends/headpics/"+randomPics[index];
    }


    @Test
    public void testRepositories() {
        userRepository.deleteAll();
        momentRepository.deleteAll();
        messageRepository.deleteAll();
        User zqq = new User("zqq@wlws.com", "123456", "朱七七", getRandomHeadPic(), "君子慎独，不欺暗室", "男", 22, "狮子座");
        User sl = new User("sl@wlws.com", "123456", "沈浪", getRandomHeadPic(), "大隐隐于市", "男", 22, "水瓶座");
        User wlh = new User("wlh@wlws.com", "123456", "王怜花", getRandomHeadPic(), "宁教我负天下人，不可天下人负我", "男", 24, "天蝎座");
        zqq.makeFriendsWith(sl);
        sl.makeFriendsWith(wlh);
        userRepository.save(zqq);
        userRepository.save(wlh);
        System.out.println("添加用户成功");
        for (int i = 0; i < 3; i++) {
            Moment moment = new Moment("大家好，我是朱七七", String.valueOf(System.currentTimeMillis()), defaultPic);
            moment.setOwner(zqq);
            momentRepository.save(moment);
            System.out.println("存入第" + i + "条动态成功");
        }
        for (int i = 0; i < 2; i++) {
            Moment moment = new Moment( "我是高冷的沈浪", String.valueOf(System.currentTimeMillis()), defaultPic);
            moment.setOwner(sl);
            moment.likedByUser(zqq);
            momentRepository.save(moment);
            System.out.println("存入第" + i + "条动态成功");
        }
        Message message1 = new Message("沈浪，你什么时候回家？", String.valueOf(System.currentTimeMillis()),zqq);
        message1.likedByUser(zqq);
        sl.addMessage(message1);
        Message message2 = new Message( "沈浪，我还等着你娶我呢。", String.valueOf(System.currentTimeMillis()),wlh);
        message2.likedByUser(sl);
        sl.addMessage(message2);
        messageRepository.save(message1);
        messageRepository.save(message2);
        System.out.println("插入留言成功！");
        userRepository.save(sl);
    }

    @Test
    public void addMessages(){
        User sl = userRepository.findByEmail("sl@wlws.com");
        User wlh = userRepository.findByEmail("wlh@wlws.com");
        for (int i = 0; i<10; i++){
            Message message2 = new Message( "蒹葭苍苍，白露为霜，所谓伊人，在水一方。", String.valueOf(System.currentTimeMillis()),wlh);
            sl.addMessage(message2);
            messageRepository.save(message2);
        }
        for (int i = 0; i<10; i++){
            Message message2 = new Message( "君住长江头，我住长江尾。", String.valueOf(System.currentTimeMillis()),wlh);
            message2.likedByUser(sl);
            sl.addMessage(message2);
            messageRepository.save(message2);
        }
        System.out.println("插入成功");
    }

    @Test
    public void addUser(){
        User fn = new User("123@wlws.com", "123456", "封楠小撒叽", getRandomHeadPic(), "哐哐哐", "女", 22, "天蝎座");
        userRepository.save(fn);
        for (int i = 0; i < 2; i++) {
            Moment moment = new Moment("今天天气真好", String.valueOf(System.currentTimeMillis()), defaultPic);
            moment.setOwner(fn);
            momentRepository.save(moment);
            System.out.println("存入第" + i + "条动态成功");
        }
    }

    @Test
    public void findAllByName(){
        List<User> users = userRepository.findAllByName("朱七七");
        System.out.println(users.size());
    }

    @Test
    public void testFindMyMoments(){
        List<String> emails=new ArrayList<>();
        emails.add("zqq@wlws.com");
        emails.add("sl@wlws.com");
        List<Moment> res=momentRepository.findMomentsByUsers(emails,0,10);
        System.out.println(res.size());
    }

//    @Test
//    public void testFindFriendsMoments(){
//        List<Moment> res=momentRepository.findFriendsMoments("sl@wlws.com",0,10);
//        System.out.println(res.size());
//    }

    @Test
    public void testFindMessagesByAccount(){
        List<Message> res=messageRepository.findMessagesByAccount("sl@wlws.com",0,11);
        System.out.println(res.size());
    }

}
