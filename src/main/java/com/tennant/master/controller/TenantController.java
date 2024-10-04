package com.tennant.master.controller;

import com.tennant.master.constants.MessageKeyConstants;
import com.tennant.master.dto.request.TenantRequest;
import com.tennant.master.dto.response.ApiGetResponse;
import com.tennant.master.dto.response.ApiResponse;
import com.tennant.master.service.TenantService;
import com.tennant.master.utils.CommonUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apis/tenant")
public class TenantController {

    private final MessageSource messageSource;
    private final TenantService tenantService;

    @PostMapping
    public Mono<ResponseEntity<ApiResponse>> createUser(@RequestBody @Valid TenantRequest tenantRequest) {
        return tenantService.createTenant(tenantRequest)
                .flatMap(apiResponse -> CommonUtils.successMessage(MessageKeyConstants.SUCCESS, MessageKeyConstants.ERROR_CODE_201, messageSource, apiResponse))
                .map(apiResponse -> ResponseEntity.status(HttpStatus.CREATED).body(apiResponse));
    }

    @GetMapping
    public Mono<ResponseEntity<ApiGetResponse>> getTenant(@RequestParam UUID tenantId) {
        return tenantService.getTenant(tenantId)
                .flatMap(apiGetResponse -> CommonUtils.successMessage(MessageKeyConstants.SUCCESS, MessageKeyConstants.ERROR_CODE_200, messageSource, apiGetResponse))
                .map(apiGetResponse -> ResponseEntity.status(HttpStatus.OK).body(apiGetResponse));
    }

}