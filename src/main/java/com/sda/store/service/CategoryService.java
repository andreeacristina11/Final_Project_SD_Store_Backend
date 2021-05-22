package com.sda.store.service;

import com.sda.store.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);
    List<Category> findAll();
    Category update(Category category);
    void delete(Long id);
    Category findById(Long id);
    Category findByName(String name);
    List<Category> findAllRootObjects();

}
