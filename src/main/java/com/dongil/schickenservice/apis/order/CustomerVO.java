package com.dongil.schickenservice.apis.order;

import lombok.Data;

@Data
public class CustomerVO {
    private String email;
    private String phoneNumber;
    private String name;
    private String address;
    private String coorX;
    private String coorY;
    private String password;
}
