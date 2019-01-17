package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Message;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends Neo4jRepository<Message,Long> {

    //根据Id获取留言
    @Query("match (m:Message) where ID(m)={id} return m")
    Message findMessageById(@Param("id")Long id);



}
