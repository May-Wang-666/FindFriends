package cn.edu.zju.socialnetwork;

import cn.edu.zju.socialnetwork.entity.User;
import cn.edu.zju.socialnetwork.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceTest extends BaseTest {

    @Autowired
    UserService userService;

    @Test
    public void testUserService() {

        /*List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }*/

    }
}
