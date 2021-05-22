package com.sda.store.sdastore;

import com.sda.store.model.Category;
import com.sda.store.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void cleanup(){
        categoryRepository.findAll().forEach(category ->
                categoryRepository.delete(category));
    }
    @Test
    public void testSaveCategoryWithSubcategory() {
        Category parent = new Category();
        parent.setName("Parent-category");

        Category subCategory1 = new Category();
        subCategory1.setName("SubCategory1");
        subCategory1.setParent(parent);

        Category subCategory2 = new Category();
        subCategory2.setName("SubCategory2");
        subCategory2.setParent(parent);

        List<Category> child = new ArrayList<>();
        child.add(subCategory1);
        child.add(subCategory2);
        parent.setSubCategories(child);

        Category savedParent = categoryRepository.save(parent);
        Assertions.assertEquals(parent.getName(), savedParent.getName());
        Assertions.assertEquals(child.size(), savedParent.getSubCategories().size());
        savedParent.getSubCategories().forEach(subCategory -> {
            Assertions.assertNotNull(subCategory.getId());
        });
    }

    @Test
    public void testDeleteCategoryAndSubcategory() {
        Category parent = new Category();
        parent.setName("Parent-category");

        Category subCategory1 = new Category();
        subCategory1.setName("SubCategory1");
        subCategory1.setParent(parent);

        Category subCategory2 = new Category();
        subCategory2.setName("SubCategory2");
        subCategory2.setParent(parent);

        List<Category> child = new ArrayList<>();
        child.add(subCategory1);
        child.add(subCategory2);
        parent.setSubCategories(child);

        Category savedParent = categoryRepository.save(parent);
        categoryRepository.delete(savedParent);
        Assertions.assertEquals(0, categoryRepository.count());
    }
}
