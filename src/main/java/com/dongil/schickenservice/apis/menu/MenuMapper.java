package com.dongil.schickenservice.apis.menu;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuMapper {
    String NAMESPACE = "com.dongil.schickenservice.apis.menu.MenuMapper.";

    @Select("""
    SELECT
          m.id
        , m.menu
        , m.price
        , a.url
    FROM menu m
        LEFT JOIN attach a ON a.parent_id = m.id AND a.tbl_id='106'
    """)
    List<MenuVO> getMenus();

    @Select("""
    SELECT
        id
      , name
    FROM  menu_category
    WHERE id=#{categoryId}
    """)
    @Results(id="getCategoryVO", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "name", property = "name"),
            @Result(column = "id", property = "menus", many = @Many(select = NAMESPACE + "getCategoryMenus"))
    })
    CategoryVO getMenusASCategoryVO(String categoryId);

    @Select("""
    SELECT
          m.id
        , m.menu
        , m.price
        , a.url
    FROM menu m
        INNER JOIN menu_and_category mac ON mac.menu_id = m.id
        LEFT JOIN attach a ON a.parent_id = m.id AND a.tbl_id='106'
    WHERE mac.menu_category = #{categoryId}
    """)
    List<MenuVO> getCategoryMenus(String categoryId);

    @Select("""
    SELECT id, name
    FROM menu_category
    """)
    List<CategoryVO> getCategories();

    @Insert("""
    INSERT INTO menu_category(name)
    VALUES (#{name})
    """)
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    int insertCategory(CategoryVO categoryVO);

    @Select(value = """
    SELECT id, name
    FROM menu_category
    WHERE id = #{categoryId}
    """)
    CategoryVO getCategory(String categoryId);

    @Select("""
    SELECT m.id, m.menu, m.price, a.url
    FROM menu m
        LEFT JOIN attach a ON a.parent_id = m.id AND a.tbl_id='106'
    WHERE id = #{menuId}
    """)
    MenuVO getMenu(String menuId);

    @InsertProvider(MenuAPISqlProvider.class)
    int setMenuAndCategory(MenuIntoCategoryVO menuIntoCategoryVO);

    @Insert("""
    INSERT INTO menu (id, price, menu)
    VALUES (#{id}, #{price}, #{menu})
    """)
    @SelectKey(keyProperty = "id", before = true, statement = "SELECT nextval(seq1)", resultType = String.class)
    int insertMenu(MenuInsertVO menuVO);

    @Update("""
    UPDATE menu
    SET menu = #{menu}, price = #{price}
    WHERE id = #{id}
    """)
    int updateMenu(MenuInsertVO menuVO);
}
