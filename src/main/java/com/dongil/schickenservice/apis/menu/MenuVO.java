package com.dongil.schickenservice.apis.menu;

import lombok.Data;

@Data
public class MenuVO {
    private String id;
    private String menu;
    private Integer price;
    private String url;
}
