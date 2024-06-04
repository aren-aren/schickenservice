package com.dongil.schickenservice.commons.rest.response;

import lombok.Data;

import java.util.List;

@Data
public class NCPAddressResponse {
    private String status;
    private String errorMessage;
    private List<NCPAddress> addresses;

    @Data
    public static class NCPAddress{
        private String roadAddress;
        private String jibunAddress;
        private String englishAddress;
        private String x;
        private String y;
        private double distance;
    }
}
