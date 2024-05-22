package com.dongil.schickenservice.apis.menu;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuDAO menuDAO;

    public List<MenuVO> getMenus() {
        return menuDAO.getMenus();
    }

    public CategoryVO getMenus(String categoryId) throws NotFoundException {
        CategoryVO category = menuDAO.getMenusASCategoryVO(categoryId);

        if(category == null) throw new NotFoundException("categoryId none");

        if(category.getId().equals("0")){
            category.setMenus(getMenus());
        }

        return category;
    }

    public List<CategoryVO> getCategories() {
        return menuDAO.getCategories();
    }

    @Transactional
    public CategoryVO setCategory(CategoryVO categoryVO) {
        int result = menuDAO.insertCategory(categoryVO);

        if(result == 0) throw new RuntimeException("insert 실패");

        return menuDAO.getCategory(categoryVO.getId());
    }

    public MenuVO getMenu(String menuId) {
        return menuDAO.getMenu(menuId);
    }

    @Transactional
    public CategoryVO setMenuAndCategory(MenuIntoCategoryVO menuIntoCategoryVO) {
        int result = menuDAO.setMenuAndCategory(menuIntoCategoryVO);

        if(result == 0) throw new RuntimeException("update 실패");

        return menuDAO.getMenusASCategoryVO(menuIntoCategoryVO.getCategoryId());
    }
}
