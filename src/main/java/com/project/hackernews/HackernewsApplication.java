package com.project.hackernews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class HackernewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackernewsApplication.class, args);
    }

}
