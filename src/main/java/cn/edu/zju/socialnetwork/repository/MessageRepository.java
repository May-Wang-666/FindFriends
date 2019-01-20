package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Message;
import cn.edu.zju.socialnetwork.entity.Moment;
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


    // 返回当前用户当前页的动态
    @Query("match p=(m:Moment)-[b:belongs_to]->(owner:User) where owner.email={email} with p,m,b,owner "+
            "optional match (lu:User)-[l:liked]->(m) return p,l,lu order by m.time desc skip {skip} limit {limit}")
    List<Moment> findMyMoments(@Param("email") String email,@Param("skip") int skip,@Param("limit") int limit);

    // 获取某个用户留言的分页接口
    @Query("match p= (u:User)-[:have]->(m:Message) where u.email ={email} " +
            "with p,m " +
            "match (lvu:User)-[lv:leaves]->(m) " +
            "with p,m,lv " +
            "optional match (lku:User)-[lk:liked]->(m) " +
            "return p,lv,lk order by m.time desc skip {skip} limit {limit}")
    List<Message> findMessageByUser(@Param("email") String email,@Param("skip") int skip,@Param("limit") int limit);

    @Query("match p= (u:User)-[:have]->(m:Message) where u.email ={email} " +
            "with p,m " +
            "match (lvu:User)-[lv:leaves]->(m) " +
            "with p,m,lv " +
            "optional match (lku:User)-[lk:liked]->(m) " +
            "return p,lv,lk order by m.time desc skip {skip} limit {limit}")
    List<Message> findMessagesByAccount(@Param("email") String email,@Param("skip") int skip,@Param("limit") int limit);

}

