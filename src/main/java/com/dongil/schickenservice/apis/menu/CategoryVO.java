package com.dongil.schickenservice.apis.menu;

import lombok.Data;

import java.util.List;

@Data
public class CategoryVO {
    String id;
    String name;

    List<MenuVO> menus;
}
