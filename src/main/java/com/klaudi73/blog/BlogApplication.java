package com.klaudi73.blog;

import com.klaudi73.blog.models.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication {

    public static UserEntity user;

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
