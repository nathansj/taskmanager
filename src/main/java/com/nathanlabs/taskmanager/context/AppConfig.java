package com.nathanlabs.taskmanager.context;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@ComponentScan(basePackages = "com.nathanlabs.taskmanager")
@EnableMongoRepositories
@EnableWebMvc
public class AppConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public Mongo mongo() throws UnknownHostException {
        return new MongoClient();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "taskmanager");
    }
}