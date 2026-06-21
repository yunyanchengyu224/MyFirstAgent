package com.sqnu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class AgentApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgentApplication.class,args);
        System.out.println("=========================================");
        System.out.println("AI面试官web服务已启动");
        System.out.println("请在浏览器访问:http://localhost:8080");
        System.out.println("=======================================");
    }
}
