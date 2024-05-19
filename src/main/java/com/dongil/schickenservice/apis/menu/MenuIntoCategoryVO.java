package com.dongil.schickenservice.apis.menu;

import lombok.Data;

@Data
public class MenuIntoCategoryVO {
    private String categoryId;
    private String[] menuIds;
}
