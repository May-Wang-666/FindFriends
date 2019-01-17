package cn.edu.zju.socialnetwork;

import cn.edu.zju.socialnetwork.response.HomePageInfo;
import cn.edu.zju.socialnetwork.service.GeneralService;
import cn.edu.zju.socialnetwork.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ControllerTest extends BaseTest {

    @Autowired
    UserService userService;

    @Autowired
    GeneralService generalService;

    @Test
    public void testHomePage(){
        HomePageInfo info = generalService.getHomePage("sl@wlws.com","sl@wlws.com");
        System.out.println(info);
    }


}
