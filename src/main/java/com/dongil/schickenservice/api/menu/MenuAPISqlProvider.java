package com.dongil.schickenservice.api.menu;

import org.apache.ibatis.builder.annotation.ProviderMethodResolver;
import org.apache.ibatis.jdbc.SQL;

public class MenuAPISqlProvider implements ProviderMethodResolver {
    public static String setMenuAndCategory(MenuIntoCategoryVO menuIntoCategoryVO){
        return new SQL(){{
            INSERT_INTO("menu_and_category");
            INTO_COLUMNS("menu_category", "menu_id");
            for (String menuId : menuIntoCategoryVO.getMenuIds()) {
                INTO_VALUES(menuIntoCategoryVO.getCategoryId(), menuId);
                ADD_ROW();
            }
        }}.toString();
    }
}
