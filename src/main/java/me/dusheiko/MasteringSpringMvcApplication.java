package me.dusheiko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import me.dusheiko.config.PictureUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({PictureUploadProperties.class})
public class MasteringSpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(MasteringSpringMvcApplication.class, args);
    }
}
