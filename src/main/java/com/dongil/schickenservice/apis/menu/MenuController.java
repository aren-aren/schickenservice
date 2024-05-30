package com.dongil.schickenservice.apis.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin
public class MenuController {

    private final MenuService service;

    @GetMapping("menus")
    public ResponseEntity<List<MenuVO>> getMenus(){
        List<MenuVO> menus = service.getMenus();

        if(menus == null || menus.isEmpty()){
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(menus);
    }

    @GetMapping("menus/{menuId}")
    public ResponseEntity<MenuVO> getMenu(@PathVariable String menuId){
        try {
            MenuVO menu = service.getMenu(menuId);

            if(menu == null){
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(menu);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("menus")
    public ResponseEntity<MenuVO> insertMenu(MenuInsertVO menuVO){
        try{
            MenuVO createdMenu = service.insertMenu(menuVO);
            log.info("created : {}", createdMenu);
            return ResponseEntity.ok(createdMenu);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("menus/{menuId}")
    public ResponseEntity<MenuVO> updateMenu(MenuInsertVO menuVO, @PathVariable String menuId){
        try{
            menuVO.setId(menuId);
            MenuVO updatedMenu = service.updateMenu(menuVO);
            log.info("updated : {}", updatedMenu);
            return ResponseEntity.ok(updatedMenu);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("menus/category/{categoryId}")
    public ResponseEntity<?> getMenusByCategory(@PathVariable String categoryId){
        try{
            CategoryVO category = service.getMenus(categoryId);

            return ResponseEntity.ok(category);
        } catch (NotFoundException e){
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("menus/category/{categoryId}")
    public ResponseEntity<CategoryVO> setMenuAndCategory(@PathVariable String categoryId, @RequestBody MenuIntoCategoryVO menuIntoCategoryVO){
        try{
            menuIntoCategoryVO.setCategoryId(categoryId);
            CategoryVO categoryVO = service.setMenuAndCategory(menuIntoCategoryVO);

            if(categoryVO == null) return ResponseEntity.notFound().build();

            return ResponseEntity.ok(categoryVO);
        } catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("categories")
    public ResponseEntity<List<CategoryVO>> getCategories(){
        List<CategoryVO> categories = service.getCategories();

        if(categories == null || categories.isEmpty()){
            ResponseEntity.notFound();
        }

        return ResponseEntity.ok(categories);
    }

    @PostMapping("categories")
    public ResponseEntity<CategoryVO> setCategory(@RequestBody CategoryVO categoryVO){
        try {
            CategoryVO created = service.setCategory(categoryVO);
            if(created == null){
                ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(created);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
