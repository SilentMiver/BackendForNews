package org.example;

import org.example.models.New;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Date;


@SpringBootApplication
public class BackServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackServerApplication.class, args);
    }
}
