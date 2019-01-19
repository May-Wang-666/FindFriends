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
import org.springframework.core.env.Environment;

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

    @Autowired
    Environment env;

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


    @Test
    public void test(){
        System.out.println(env.getProperty("visit.path"));
    }


}
