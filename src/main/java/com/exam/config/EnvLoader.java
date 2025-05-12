package com.exam.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class EnvLoader {
    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}