package com.bajagym.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import java.util.Collections;
import java.util.HashMap;

@SpringBootApplication
public class AppInitializer {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AppConfiguration.class);
        application.setDefaultProperties(new HashMap<String, Object>() {{
            put("spring.main.allow-bean-definition-overriding", true);
        }});
        application.run();
    }



}
