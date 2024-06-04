package com.dongil.schickenservice.commons.rest;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class NaverCloudRestConfig {
    @Value("${navercloud.clientId}")
    private String ncpId;
    @Value("${navercloud.clientKey}")
    private String ncpKey;

    @Bean
    RequestInterceptor requestInterceptor(){
        return requestTemplate -> requestTemplate
                .header("X-NCP-APIGW-API-KEY-ID", ncpId)
                .header("X-NCP-APIGW-API-KEY", ncpKey);
    }
}
