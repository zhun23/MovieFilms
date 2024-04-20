package com.example.dev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.example.dev")
@EnableJpaRepositories("com.example.dev.dao")
public class MovieFilmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieFilmsApplication.class, args);
        
    }
}

