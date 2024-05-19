package com.dongil.schickenservice.api.menu;

import lombok.Data;

@Data
public class MenuIntoCategoryVO {
    private String categoryId;
    private String[] menuIds;
}
