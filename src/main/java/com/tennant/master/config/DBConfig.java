package com.tennant.admin.config;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class DBConfig extends AbstractR2dbcConfiguration {

    @Value("${tenant.dbConfig}")
    private String dbConfig;

    @Value("${spring.r2dbc.url}")
    private String r2dbcUrl;

    @Override
    @NonNull
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(r2dbcUrl);
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator cdp = new CompositeDatabasePopulator();
        if (dbConfig.equalsIgnoreCase("create")) {
            cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("admin/DropTables/DropTables.sql")));
            cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("admin/Tables/create.sql")));
            cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("admin/Tables/index.sql")));
            cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("admin/Tables/masterData.sql")));
        } else if (dbConfig.equalsIgnoreCase("update")) {
            cdp.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("admin/UpdateTables/UpdateTables.sql")));
        }
        initializer.setDatabasePopulator(cdp);
        return initializer;
    }
}
