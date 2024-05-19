package com.dongil.schickenservice.api.menu;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    String id;
    String name;

    List<MenuVO> menus;
}
