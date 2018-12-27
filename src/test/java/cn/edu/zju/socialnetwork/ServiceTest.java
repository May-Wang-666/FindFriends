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

        List<User> userList = userService.getUserByName("name5");
        for (User user:userList){
            System.out.println(user);
        }

        User accountUser = userService.getUserByAccount("wl@qq.com");
        System.out.println(accountUser);

    }

    @Test
    public void testAddUser(){

        User user1 = new User("wl","wl@qq.com","zqq","123456","18816638353","zju");
        User user2 = new User("fn","fn@qq.com","little cute","123456","12345678910","zju");
        User user3 = new User("no account","","null","null","null","null");
        User user4 = new User("no password","haha@qq.com","","","","zju");
        System.out.println(userService.addUser(user1));
        System.out.println(userService.addUser(user2));
        System.out.println(userService.addUser(user3));
        System.out.println(userService.addUser(user4));
        System.out.println(userService.addUser(user1));

    }
}
