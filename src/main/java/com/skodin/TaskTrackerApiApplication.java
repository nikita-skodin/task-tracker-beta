package com.skodin;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Log4j2
@SpringBootApplication
public class TaskTrackerApiApplication {

    // TODO: 016 добавить поля для связи многие к одному по типу owner

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
