package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Message;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends Neo4jRepository<Message, Long> {

    //根据Id获取留言
    @Query("match (m:Message) where ID(m)={id} return m")
    Message findMessageById(@Param("id") Long id);

    // 获取用户拥有的留言
    List<Message> findAllByOwnerEmailOrderByTimeDesc(@Param("ownerEmail") String email);


    // 获取某用户所有留言
    @Query("match a=(u:User)-[r:have]->(m:Message),b=(:User)-[l:liked]->(m),c=(:User)-[:leaves]->(m) where u.email={email} return a,b,c,m order by m.time desc " +
            "union " +
            "match a=(u:User)-[r:have]->(m:Message),c=(:User)-[:leaves]->(m) where u.email={email} " +
            "return a,null as b,c,m order by m.time desc")
    List<Message> findMessagesByAccount(@Param("email") String email);

    // 获取某个用户留言的分页接口
    @Query("match p= (u:User)-[:have]->(m:Message) where u.email ={email} " +
            "with p,m " +
            "match (lvu:User)-[lv:leaves]->(m) " +
            "with p,m,lv " +
            "optional match (lku:User)-[lk:liked]->(m) " +
            "return p,lv,lk order by m.time desc skip {skip} limit {limit}")
    List<Message> findMessageByUser(@Param("email") String email,@Param("skip") int skip,@Param("limit") int limit);

}

