package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    // 不同用户可能重名
    List<User> findByName(@Param("name") String name);

    // 不同用户账户必须不同
    User findByAccount(@Param("account") String account);

    @Query("match (p:User) return p")
    List<User> getAllUser();



}
