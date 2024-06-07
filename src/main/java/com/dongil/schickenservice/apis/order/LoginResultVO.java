package com.dongil.schickenservice.apis.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResultVO {
    private String result;
    private CustomerVO user;
}
