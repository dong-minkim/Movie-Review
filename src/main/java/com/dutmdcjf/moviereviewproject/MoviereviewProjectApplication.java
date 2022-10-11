package com.dutmdcjf.moviereviewproject;

import com.dutmdcjf.moviereviewproject.dto.UploadResultDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.lang.reflect.Field;

@SpringBootApplication
@EnableJpaAuditing
public class MoviereviewProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoviereviewProjectApplication.class, args);
    }

}
