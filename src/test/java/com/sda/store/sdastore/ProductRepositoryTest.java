package com.sda.store.sdastore;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import com.sda.store.repository.CategoryRepository;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.ProductSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public  void cleanup() {
        productRepository.findAll().forEach(product -> productRepository.delete(product));
        categoryRepository.findAll().forEach(category -> categoryRepository.delete(category));

    }

    @Test
    public void testSave() {
        Category drinks = new Category();
        drinks.setName("Driinks");
        drinks = categoryRepository.save(drinks);

        Product cocaCola = new Product();
        cocaCola.setDescription("Coca-cola");
        cocaCola.setPrice(2.0);
        cocaCola.setCategory(drinks);
        cocaCola.setProductType(ProductType.BEVERAGES);

        cocaCola = productRepository.save(cocaCola);

        Assertions.assertNotNull(cocaCola.getId());
        Assertions.assertNotNull(cocaCola.getCategory());
        Assertions.assertNotNull(cocaCola.getName());
    }

        @Test
         public void searchProductsByName() {
            Product tv = new Product();
            tv.setName("LG");

            Product samsung = new Product();
            samsung.setName("Samsung");

            productRepository.save(tv);
            productRepository.save(samsung);

            Assertions.assertEquals(2, productRepository.count());
            Assertions.assertEquals(1,
                    productRepository.findAll(ProductSpecification.withNameLike("LG")).size());
            Assertions.assertEquals(1,
                    productRepository.findAll(ProductSpecification.withNameLike("Samsung")).size());
            Assertions.assertEquals(0,
                    productRepository.findAll(ProductSpecification.withNameLike("Phillipes")).size());
        }

        @Test
    public void searchProcutsByProductType(){
        Product appliance = new Product();
        appliance.setProductType(ProductType.APPLIANCES);

        Product food = new Product();
        food.setProductType(ProductType.FOOD);

        productRepository.save(appliance);
        productRepository.save(food);

        Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.ofType(ProductType.FOOD)).size());
            Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.ofType(ProductType.APPLIANCES)).size());
            Assertions.assertEquals(0, productRepository.findAll(ProductSpecification.ofType(ProductType.COSMETICS)).size());

        }

        @Test
        public void searchProductsByProductTypeAndName(){
        Product samsungPhone = new Product();
        samsungPhone.setName("Samsung");
        samsungPhone.setProductType(ProductType.MOBILE_PHONES);

        Product samsungTV = new Product();
        samsungTV.setName("Samsung");
        samsungTV.setProductType(ProductType.APPLIANCES);

        productRepository.save(samsungPhone);
        productRepository.save(samsungTV);

        Assertions.assertEquals(2, productRepository.findAll(ProductSpecification.withNameLike("Samsung")).size());
            Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.withNameLike("Samsung").and(ProductSpecification.ofType(ProductType.MOBILE_PHONES))).size());
        }

        @Test
    public  void searchProductsByPriceRange(){
        Product phone1 = new Product();
        phone1.setPrice(150.0);

         Product phone2 = new Product();
         phone2.setPrice(170.0);

         Product phone3 = new Product();
         phone3.setPrice(300.0);

         productRepository.save(phone1);
         productRepository.save(phone2);
         productRepository.save(phone3);

         Assertions.assertEquals(2, productRepository.findAll(ProductSpecification.withPriceInRange(0.0, 200.0)).size());
            Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.withPriceInRange(300.0, 500.0)).size());
        }


    @Test
    public  void searchProductsByCategoryId() {
        Category applianceCategory = new Category();
        applianceCategory.setName("Appliance_category");

        Category phonesCategory = new Category();
        phonesCategory.setName("Phone_category");

        categoryRepository.save(applianceCategory);
        categoryRepository.save(phonesCategory);

        Product appliance = new Product();
        appliance.setName("Appliance_1");
        appliance.setCategory(applianceCategory);

        productRepository.save(appliance);

        Product phone = new Product();
        phone.setName("Phone_1");
        phone.setCategory(phonesCategory);

        productRepository.save(phone);

        Product phone2 = new Product();
        phone2.setName("Phone_2");
        phone2.setCategory(phonesCategory);

        productRepository.save(phone2);

        Assertions.assertEquals(1, productRepository.findAll(ProductSpecification.withCategoryId(applianceCategory.getId())).size());
        Assertions.assertEquals(2, productRepository.findAll(ProductSpecification.withCategoryId(phonesCategory.getId())).size());
    }

    }

