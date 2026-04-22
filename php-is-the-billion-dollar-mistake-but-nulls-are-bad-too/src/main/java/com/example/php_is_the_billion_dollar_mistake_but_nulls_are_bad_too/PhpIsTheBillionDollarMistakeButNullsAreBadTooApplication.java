package com.example.php_is_the_billion_dollar_mistake_but_nulls_are_bad_too;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class PhpIsTheBillionDollarMistakeButNullsAreBadTooApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhpIsTheBillionDollarMistakeButNullsAreBadTooApplication.class, args);
    }

}

@Component
class MyRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        IO.println("salut!");
    }
}