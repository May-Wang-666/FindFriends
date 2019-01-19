package cn.edu.zju.socialnetwork;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.repository.MessageRepository;
import cn.edu.zju.socialnetwork.repository.MomentRepository;
import cn.edu.zju.socialnetwork.repository.UserRepository;
import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.response.ResponseMessages;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class ServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    GeneralService generalService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MomentRepository momentRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void testHomePage(){
        HomePageInfo info = generalService.getHomePage("zqq@wlws.com","sl@wlws.com");
        System.out.println(info);
    }

    @Test
    public void testMomentRepository(){
        User zqq = userRepository.findByEmail("sl@wlws.com");
        //List<Moment> moments = momentRepository.findAllByOwnerEmail(zqq.getEmail());
        //List<Moment> moments = momentRepository.findAllByOwner(zqq);
        List<Moment> moments = momentRepository.findAllByOwnerEmailOrderByTimeDesc(zqq.getEmail());
        System.out.println(moments.size());
        for (Moment moment:moments){
            System.out.println(moment);
        }
    }

    @Test public void testMessage(){

        List<Message> messages = messageRepository.findAllByOwnerEmailOrderByTimeDesc("sl@wlws.com");
        System.out.println(messages.size());

    }

    @Test
    public void test(){
        HashMap<String,String> map = new HashMap<>();
        map.put("1","woshiyi");
        map.put("2","woshier");
        System.out.println(map);
    }


}
