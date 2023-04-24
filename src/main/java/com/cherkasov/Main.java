package com.cherkasov;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Objects;

@EnableJpaRepositories("com.cherkasov.repositories")
@EntityScan("com.cherkasov.models")
@SpringBootApplication
@Log4j2
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}