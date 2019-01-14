package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    // 添加用户
    // void save();

    // 根据节点id获取用户
    @Query("match (n:User) where ID(n) = {id}")
    User findUserById(@Param("id") Long id);

    // 根据邮箱获取用户
    User findByEmail(@Param("email")String email);

    // 返回一个用户的好友列表
    @Query("match (u:User){email:{email}}-[:is_friends_with]->(p:User) return p")
    List<User> findFriends(@Param("email") String email);

    // 返回所有动态
    @Query("match (m:Moment)-[:blongs_to]->(:User){email:{email}}")
    List<Moment> getMoments(@Param("email") String email);

    // 修改信息
    @Query("MATCH (user:User)WHERE user.email={email} SET user.nickname = {nickname},user.sex={sex},user.age={age},user.motto={motto},user.xinzuo={xinzuo} return user")
    User modifyName(@Param("email") String email, @Param("nickname") String nickname, @Param("sex")String sex, @Param("xinzuo")String xinzuo,@Param("age") int age, @Param("motto")String motto);

    // 修改头像
    @Query("match (u:User) where u.email={email} set u.headpic={headpic} return u")
    User modifyHeadpic(@Param("email") String email, @Param("headpic")String headpic);

}
