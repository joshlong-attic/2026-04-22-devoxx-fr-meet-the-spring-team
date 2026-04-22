package com.example.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Collection;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    JdbcPostgresDialect postgresDialect() {
        return JdbcPostgresDialect.INSTANCE;
    }

    @Bean
    ApplicationRunner runner(DogRepository repository) {
        return (_) -> repository.findByName("Prancer").forEach(IO::println);
    }
}

interface DogRepository extends ListCrudRepository<Dog, Integer> {

    Collection<Dog> findByName(String name);
}

// look mom, no Lombok!!
record Dog(@Id int id, String name) {
}
