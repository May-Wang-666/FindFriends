package cn.edu.zju.socialnetwork;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

public class ServiceTest extends BaseTest {

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
            localAddress = InetAddress.getLocalHost().getHostAddress();
            defaultPic = "http://" + localAddress + ":8080/headpics/default_headpic.png";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        super.init();
    }

    private String getRandomHeadPic(){
        Random random = new Random();
        int index = random.nextInt(4);
        return "http://"+localAddress+":8080/headpics/"+randomPics[index];
    }

    @Test
    public void testUserService() {
       /* User fn = new User("123@qq.com", "123456", "封楠小撒叽", defaultPic, "哐哐哐", "女", 3, "天蝎座");
        userRepository.save(fn);*/
        User fn = userRepository.findByEmail("123@qq.com");
        Message message = new Message("测试",String.valueOf(System.currentTimeMillis()),fn);
        User wlh = userRepository.findByEmail("wlh@wlws.com");
        wlh.addMessage(message);
        userRepository.save(wlh);
    }

    @Test
    public void testAutoInsert() {
        User user = userRepository.findByEmail("wlh@wlws.com");
        System.out.println(user);
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
            moment.likedByUser(zqq);
            moment.likedByUser(wlh);
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
    public void testUserRepository() {
        List<Moment> moments = userRepository.findMyMoments("zqq@wlws.com",1);
        // List<Message> messages = userRepository.findMessages("sl@wlws.com");
        for (Moment moment : moments) {
            System.out.println(moment);
        }
        //System.out.println("留言数："+messages.size());

    }
}
