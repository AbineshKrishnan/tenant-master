package com.tennant.master.service.impl;


import com.tennant.master.constants.MessageKeyConstants;
import com.tennant.master.dto.request.TenantRequest;
import com.tennant.master.dto.response.ApiGetResponse;
import com.tennant.master.dto.response.ApiResponse;
import com.tennant.master.exception.ResourceNotFoundException;
import com.tennant.master.persistence.entity.Tenant;
import com.tennant.master.persistence.repository.TenantRepository;
import com.tennant.master.service.TenantService;
import com.tennant.master.support.ServiceSupport;
import com.tennant.master.utils.mapper.TenantMapper;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@RequiredArgsConstructor
@Service
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final ServiceSupport serviceSupport;
    private final DatabaseClient databaseClient;

    @Override
    public Mono<ApiGetResponse> getTenant(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(MessageKeyConstants.USER_NOT_FOUND)))
                .map(tenantMapper::mapToTenantResponse)
                .flatMap(serviceSupport::getResponse);
    }

    @Override
    public Mono<ApiResponse> createTenant(TenantRequest tenantRequest) {
        return Mono.just(tenantRequest)
                .map(tenantMapper::mapToTenant)
                .flatMap(tenantRepository::save)
                .flatMap(this::initializeDatabaseForTenant)
                .thenReturn(new ApiResponse());
    }

    private Mono<Void> initializeDatabaseForTenant(Tenant tenant) {
        return createTenantDatabase(tenant)
                .then(initializeSchemaForTenant(tenant));
    }

    private Mono<Void> createTenantDatabase(Tenant tenant) {
        return databaseClient.sql("CREATE DATABASE " + tenant.getDbName())
                .fetch().rowsUpdated().then();
    }

    private Mono<Void> initializeSchemaForTenant(Tenant tenant) {
        ConnectionFactory tenantFactory = createTenantConnectionFactory(tenant);
        DatabaseClient tenantDatabaseClient = DatabaseClient.builder().connectionFactory(tenantFactory).build();
        CompositeDatabasePopulator databasePopulate = new CompositeDatabasePopulator();
        databasePopulate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("tenant/DropTables/DropTables.sql")));
        databasePopulate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("tenant/tables/create.sql")));
        databasePopulate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("tenant/tables/masterData.sql")));
        databasePopulate.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("tenant/tables/index.sql")));
        return tenantDatabaseClient.inConnection(connection -> Mono.from(databasePopulate.populate(connection)));
    }

    private ConnectionFactory createTenantConnectionFactory(Tenant tenant) {
        return ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, tenant.getDriver())
                .option(HOST, tenant.getDbUrl())
                .option(PORT, tenant.getPort())
                .option(USER, tenant.getDbUsername())
                .option(PASSWORD, tenant.getDbPassword())
                .option(DATABASE, tenant.getDbName())
                .build());
    }

}
