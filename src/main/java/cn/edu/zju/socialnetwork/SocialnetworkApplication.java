package cn.edu.zju.socialnetwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@ServletComponentScan
@SpringBootApplication
public class SocialnetworkApplication {
    public static void main(String[] args){
        SpringApplication.run(SocialnetworkApplication.class,args);
    }
}