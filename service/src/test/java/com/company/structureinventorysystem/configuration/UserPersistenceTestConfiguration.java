package com.company.structureinventorysystem.configuration;

import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class UserPersistenceTestConfiguration extends PersistenceTestConfiguration {

    @Override
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setName("user_service_test")
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_schema.sql")
                .addScript("user_test_tables.sql")
                .addScript("user_test_sample.sql")
                .build();
    }

}
