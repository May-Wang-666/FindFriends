package cn.edu.zju.socialnetwork.repository;

import cn.edu.zju.socialnetwork.entity.Moment;
import cn.edu.zju.socialnetwork.entity.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MomentRepository extends Neo4jRepository<Moment,Long> {

    // 根据id获取动态
    @Query("match p=(m:Moment)-[:belongs_to]->(:User) where ID(m)={id} with p,m " +
            "optional match l=(:User)-[:liked]->(m) return p,l")
    Moment findMomentById(@Param("id") Long id);

    List<Moment> findAllByOwnerEmailOrderByTimeDesc(@Param("ownerEmial") String email);

    /**
     * 根据用户Email列表查询这些用户所发的朋友圈，结果按时间排序，带有分页功能
     * @param emails 需要查询的用户邮箱列表
     * @param skip 实现分页功能，跳过前N条
     * @param limit 实现分页功能，只显示M条
     * @return List<Moment>
     */
    @Query("match p=(m:Moment)-[b:belongs_to]->(owner:User) where owner.email in {emails} with p,m,b,owner "+
            "optional match (lu:User)-[l:liked]->(m) return p,l,lu order by m.time desc skip {skip} limit {limit}")
    List<Moment> findMomentsByUsers(@Param("emails") List<String> emails,@Param("skip") int skip,@Param("limit") int limit);


    // 返回当前用户当前页的动态
    @Query("match p=(m:Moment)-[b:belongs_to]->(owner:User) where owner.email={email} with p,b,owner, m order by m.time desc skip {skip} limit {limit} "+
            "optional match (lu:User)-[l:liked]->(m) return p,l,lu ")
    List<Moment> findMyMoments(@Param("email") String email,@Param("skip") int skip,@Param("limit") int limit);


    // 获取某用户的动态总数
    @Query("match (m:Moment)-[r:belongs_to]->(u:User) where u.email={email} return count(m)")
    int findNumOfMoments(@Param("email") String email);



}
