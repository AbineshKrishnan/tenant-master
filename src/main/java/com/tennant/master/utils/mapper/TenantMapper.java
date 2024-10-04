package com.tennant.master.utils.mapper;


import com.tennant.master.dto.request.TenantRequest;
import com.tennant.master.dto.response.TenantResponse;
import com.tennant.master.persistence.entity.Tenant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    Tenant mapToTenant(TenantRequest tenantRequest);

    TenantResponse mapToTenantResponse(Tenant tenant);

}