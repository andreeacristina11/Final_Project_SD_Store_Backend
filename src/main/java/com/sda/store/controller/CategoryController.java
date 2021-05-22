package com.sda.store.controller;

import com.sda.store.controller.dto.category.CategoryRequestDto;
import com.sda.store.controller.dto.category.CategoryResponseDto;
import com.sda.store.model.Category;
import com.sda.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CategoryRequestDto dto){
        Category category = categoryService.create(mapCategoryRequestDtoToCategory(dto));
        return mapCategoryToCategoryResponseDto(category);

    }

    @GetMapping
    public List<CategoryResponseDto> findAlLRootObjects(){
        List<Category>categoryList = categoryService.findAllRootObjects();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category c : categoryList){
            categoryResponseDtoList.add(mapCategoryToCategoryResponseDto(c));
        }
        return categoryResponseDtoList;
    }

    @GetMapping(path = "/all")
    public List<CategoryResponseDto>findAll(){
        List<Category> categoryList = categoryService.findAll();
        List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
        for (Category c: categoryList){
            categoryResponseDtoList.add(maptoCategoryResponseDtoAndName(c));
        }
        return categoryResponseDtoList;
    }

//    SAU
//    @GetMapping(path = "/all")
//    public List<CategoryResponseDto> findAll(){
//        return categoryService
//                .findAll()
//                .stream()
//                .map(el ->{
//                    CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
//                    categoryResponseDto.setName(el.getName());
//                    categoryResponseDto.setId(el.getId());
//                })
//                .collect(Collectors.toList());
//    }


    @PutMapping
    public CategoryResponseDto update(@PathVariable Long id, @RequestBody CategoryRequestDto categoryRequestDto){
        Category category = categoryService.findById(id);
        Category updateCategory = updateCategoryFromCategorResponseDto(category, categoryRequestDto);
        return mapCategoryToCategoryResponseDto(categoryService.update(updateCategory));
    }

    @DeleteMapping(value = "/delete/{id}")
    public HttpStatus delete(@PathVariable Long id){
        categoryService.delete(id);
        return HttpStatus.OK;
    }

    public Category updateCategoryFromCategorResponseDto(Category existentCategory, CategoryRequestDto categoryRequestDto){
        if (categoryRequestDto.getName() != null){
            existentCategory.setName(categoryRequestDto.getName());
        }
        if (categoryRequestDto.getParentId() != null){
            Category newParentCategory = categoryService.findById(categoryRequestDto.getParentId());
            if(existentCategory.getId().equals(newParentCategory.getId())){
                throw new RuntimeException(String.format(""));
            }existentCategory.setParent(newParentCategory);
        }
        return existentCategory;
    }

    public Category mapCategoryRequestDtoToCategory(CategoryRequestDto dto){
        Category category = new Category();
        category.setName(dto.getName());
        if (dto.getParentId() != null){
            Category parent = categoryService.findById(dto.getParentId());
            category.setParent(parent);
        }
        return category;
    }

    public CategoryResponseDto mapCategoryToCategoryResponseDto (Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        if (category.getParent() != null) {
            dto.setParentName(category.getParent().getName());

        }
        List<Category> subCategories = category.getSubCategories();
        if (subCategories != null) {
            List<CategoryResponseDto> subCategoriesDto = new ArrayList<>();
            for (Category cat : subCategories) {
                subCategoriesDto.add(mapCategoryToCategoryResponseDto(cat));
            }
            dto.setSubCategories(subCategoriesDto);

        }return dto;
    }

    public CategoryResponseDto maptoCategoryResponseDtoAndName(Category category){
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setName(category.getName());
        categoryResponseDto.setId(category.getId());
        return categoryResponseDto;
    }


}
