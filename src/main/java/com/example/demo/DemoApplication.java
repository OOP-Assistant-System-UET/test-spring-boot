package com.example.demo;

import com.jsa.model.ClassDecration;
import com.jsa.model.RelationshipList;
import com.jsa.service.ParsePackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;

@SpringBootApplication
@ComponentScan("com")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(4);
        executor.setThreadNamePrefix("jsa_thread");
        executor.initialize();
        return executor;
    }

    @Bean
    public HashMap<String, ArrayList<ClassDecration>> dataMap() {
        return new HashMap<>();
    }
    @Bean
    public HashMap<String, RelationshipList> relationMap() {
        return new HashMap<>();
    }


}
