package com.company.structureinventorysystem.web.configuration;

import com.company.structureinventorysystem.infrastructure.repository.BuildingAuditRepository;
import com.company.structureinventorysystem.infrastructure.repository.UserRepository;
import com.company.structureinventorysystem.service.BuildingAuditService;
import com.company.structureinventorysystem.service.UserService;
import com.company.structureinventorysystem.web.audit.BuildingAuditController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = {"com.company.structureinventorysystem.service", "com.company.structureinventorysystem.infrastructure"})
public class BuildingAuditControllerTestConfiguration {

    @Autowired
    public BuildingAuditRepository buildingAuditRepository;
    @Autowired
    public UserRepository userRepository;

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }

    @Bean
    public BuildingAuditService buildingAuditService() {
        return new BuildingAuditService(buildingAuditRepository);
    }

    @Bean
    public BuildingAuditController buildingAuditController() {
        return new BuildingAuditController(buildingAuditService());
    }

}
