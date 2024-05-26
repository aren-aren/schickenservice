package com.dongil.schickenservice.apis.menu;

import com.dongil.schickenservice.commons.files.FileManager;
import com.dongil.schickenservice.commons.files.FileVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuMapper menuMapper;
    private final FileManager fileManager;

    public List<MenuVO> getMenus() {
        return menuMapper.getMenus();
    }

    public CategoryVO getMenus(String categoryId) throws NotFoundException {
        CategoryVO category = menuMapper.getMenusASCategoryVO(categoryId);

        if(category == null) throw new NotFoundException("categoryId none");

        if(category.getId().equals("0")){
            category.setMenus(getMenus());
        }

        return category;
    }

    public List<CategoryVO> getCategories() {
        return menuMapper.getCategories();
    }

    @Transactional
    public CategoryVO setCategory(CategoryVO categoryVO) {
        int result = menuMapper.insertCategory(categoryVO);

        if(result == 0) throw new RuntimeException("insert 실패");

        return menuMapper.getCategory(categoryVO.getId());
    }

    public MenuVO getMenu(String menuId) {
        return menuMapper.getMenu(menuId);
    }

    @Transactional
    public CategoryVO setMenuAndCategory(MenuIntoCategoryVO menuIntoCategoryVO) {
        int result = menuMapper.setMenuAndCategory(menuIntoCategoryVO);

        if(result == 0) throw new RuntimeException("update 실패");

        return menuMapper.getMenusASCategoryVO(menuIntoCategoryVO.getCategoryId());
    }

    @Transactional
    public MenuVO insertMenu(MenuInsertVO menuVO) throws Exception {
        int result = menuMapper.insertMenu(menuVO);

        if(menuVO.getId() == null) throw new RuntimeException("insertMenu : selectKey null");
        if(result == 0) throw new RuntimeException("insertMenu : insert result 0");

        if(menuVO.getAttach() != null && !menuVO.getAttach().isEmpty()){
            FileVO uploaded = fileManager.uploadFile(menuVO.getAttach());
            uploaded.setParentId(menuVO.getId());
            uploaded.setTblId("106");
            int saveResult = fileManager.saveFile(uploaded);

            if(saveResult == 0) throw new RuntimeException("insertMenu : file save Fail");
        }

        return menuMapper.getMenu(menuVO.getId());
    }

    @Transactional
    public MenuVO updateMenu(MenuInsertVO menuVO) throws Exception {
        if(menuVO.getAttach() != null && !menuVO.getAttach().isEmpty()){
            FileVO oldFile = new FileVO();
            oldFile.setId(menuVO.getId());

            FileVO updated = fileManager.fileUpdate(oldFile, menuVO.getAttach());
            updated.setParentId(menuVO.getId());
            updated.setTblId("106");
            int saveResult = fileManager.saveFile(updated);

            if(saveResult == 0) throw new RuntimeException("insertMenu : file save Fail");
        }

        int result = menuMapper.updateMenu(menuVO);

        if(result == 0) throw new RuntimeException("updateMenu : update result 0");

        return menuMapper.getMenu(menuVO.getId());
    }
}
