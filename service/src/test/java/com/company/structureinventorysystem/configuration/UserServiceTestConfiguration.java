package com.company.structureinventorysystem.configuration;

import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import com.company.structureinventorysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceTestConfiguration {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

}
