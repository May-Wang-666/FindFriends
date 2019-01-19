package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MomentRepository extends Neo4jRepository<Moment,Long> {

    // 根据id获取动态
    @Query("match (m:Moment) where ID(m)={id} return m")
    Moment findMomentById(@Param("id") Long id);

    // 根据用户账号返回他的动态
    List<Moment> findAllByOwnerEmail(@Param("ownerEmial") String email);

    List<Moment> findByOwnerIn(@Param("owners")List<User> owners);



    List<Moment> findAllByOwnerEmailOrderByTimeDesc(@Param("ownerEmial") String email);

    // 返回当前用户及其好友的所有动态
    @Query("match (owner:User)-[f:is_friends_with]-(friends:User),(moment:Moment)-[belong:belongs_to]->(u:User),p=(:User)-[:liked]->(:Moment) " +
            "with owner,friends,moment,belong,u,p " +
            "where owner.email = {email} and u in [owner,friends] " +
            "return owner,friends,moment,belong,p " +
            "order by moment.time desc")
    List<Moment> findFriendsMoments(@Param("email") String email, @Param("page") int page);


    @Query("match (m:Moment)-[r:belongs_to]->(u:User),(p:User)-[l:liked]->(m) where u.email={email} return m,u,p,r,l order by m.time desc " +
            "union " +
            "match (m:Moment)-[r:belongs_to]->(u:User) where u.email={email} return m,u,null as p,r,null as l order by m.time desc")
    List<Moment> findMyMoments(@Param("ownerEmial") String email, @Param("page") int page);


    // Yukino




}
