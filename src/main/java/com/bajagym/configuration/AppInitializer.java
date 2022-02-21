package com.bajagym.configuration;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args){
        SpringApplication application = new SpringApplication(AppConfiguration.class);
        application.setDefaultProperties(new HashMap<String,Object>(){{put("spring.main.allow-bean-definition-overriding",true);}});
        application.run();
    }

}
