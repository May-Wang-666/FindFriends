package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    // 根据邮箱获取用户
    User findByEmail(@Param("email")String email);

    // 根据名字获取用户
    List<User> findAllByName(@Param("name") String name);

    // 返回用户留言数量
    @Query("match (u:User)-[:have]->(m:Message) where u.email={email} return count(m)")
    int findNumOfMessages(@Param("email") String email);

    // 返回用户动态数量
    @Query("match (m:Moment)-[:belongs_to]->(u:User) where u.email={email} return count(m)")
    int findNumOfMoments(@Param("email") String email);

    // 修改头像
    @Query("match (u:User) where u.email={email} set u.headpic={headpic} ")
    void modifyHeadpic(@Param("email") String email, @Param("headpic")String headpic);


    // 修改信息
    @Query("MATCH (user:User) WHERE user.email={email} SET user.nickname = {nickname},user.sex={sex},user.age={age},user.motto={motto},user.xinzuo={xinzuo} return user")
    User updateUserInfo(@Param("email") String email, @Param("nickname") String nickname, @Param("sex")String sex, @Param("xinzuo")String xinzuo, @Param("age") int age, @Param("motto")String motto);

    /* 以下为暂时没有用到的函数 */

    // 根据节点id获取用户
    @Query("match (n:User) where ID(n) = {id}")
    User findUserById(@Param("id") Long id);

}
