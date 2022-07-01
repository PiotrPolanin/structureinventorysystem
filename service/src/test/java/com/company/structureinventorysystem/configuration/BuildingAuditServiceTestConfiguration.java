package com.company.structureinventorysystem.configuration;

import com.company.structureinventorysystem.infrastructure.repository.BuildingAuditRepository;
import com.company.structureinventorysystem.service.BuildingAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildingAuditServiceTestConfiguration {

    @Autowired
    BuildingAuditRepository buildingAuditRepository;

    @Bean
    public BuildingAuditService buildingAuditService() {
        return new BuildingAuditService(buildingAuditRepository);
    }

}
