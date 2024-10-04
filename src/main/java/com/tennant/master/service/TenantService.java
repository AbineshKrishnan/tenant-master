package com.tennant.master.service;


import com.tennant.master.dto.request.TenantRequest;
import com.tennant.master.dto.response.ApiGetResponse;
import com.tennant.master.dto.response.ApiResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TenantService {

    Mono<ApiGetResponse> getTenant(UUID tenantId);

    Mono<ApiResponse> createTenant(TenantRequest tenantRequest);
}
