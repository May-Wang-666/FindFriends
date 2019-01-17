package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Moment;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

public interface MomentRepository extends Neo4jRepository<Moment,Long> {

    // 根据id获取动态
    @Query("match (m:Moment) where ID(m)={id} return m")
    Moment findMomentById(@Param("id") Long id);

}
