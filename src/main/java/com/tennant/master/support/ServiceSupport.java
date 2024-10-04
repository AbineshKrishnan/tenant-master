package com.tennant.master.support;

import com.tennant.master.dto.request.BasicRequest;
import com.tennant.master.dto.response.ApiGetResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ServiceSupport {

    public Mono<ApiGetResponse> getResponse(Object result) {
        return Mono.just(new ApiGetResponse()).doOnNext(apiGetResponse -> apiGetResponse.setResult(result));
    }

    public Pageable getPageable(BasicRequest basicRequest) {
        return PageRequest.of(basicRequest.getPageNo(), basicRequest.getPageSize());
    }

}
