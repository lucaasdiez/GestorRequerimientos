package com.srgi.security.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration

public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

   // @Bean
    //public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

}
