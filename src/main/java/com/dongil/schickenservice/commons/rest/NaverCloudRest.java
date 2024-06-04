package com.dongil.schickenservice.commons.rest;

import com.dongil.schickenservice.commons.rest.response.NCPAddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "naverCloud",
        url = "${navercloud.baseUrl}",
        configuration = NaverCloudRestConfig.class
)
public interface NaverCloudRest {
    String DATAVERSION = "171.81";

    @GetMapping("map-geocode/v2/geocode?query={query}")
    NCPAddressResponse getAddrPoint(@PathVariable("query") String address);

    @GetMapping("map-static/v2/raster?w=300&h=300&markers=pos:{coordinate}|size:small&dataversion=" + DATAVERSION)
    byte[] getAddrMap(@PathVariable("coordinate") String coordinate);
}
