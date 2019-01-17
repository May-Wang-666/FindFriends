package cn.edu.zju.socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

//扫描neo4j库操作包路径，一定要有而且要配置正确
@EnableNeo4jRepositories(basePackages = {"cn.edu.zju.socialnetwork.repository"  })
//扫描neo4j实体类包路径，重申一定要有而且要配置正确
@EntityScan(basePackages = {"cn.edu.zju.socialnetwork.entity"})
@SpringBootApplication
public class SocialnetworkApplication {
    public static void main(String[] args){
        SpringApplication.run(SocialnetworkApplication.class,args);
    }
}