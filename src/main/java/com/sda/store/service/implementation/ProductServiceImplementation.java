package com.sda.store.service.implementation;

import com.sda.store.excpetion.ResourceNotFoundInDatabase;
import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.repository.CategoryRepository;
import com.sda.store.repository.ProductRepository;
import com.sda.store.repository.ProductSpecification;
import com.sda.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImplementation(ProductRepository repository, CategoryRepository categoryRepository) {
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ResourceNotFoundInDatabase(String.format("Product with id %d does not exist", id));
        }
    }


    @Override
    public Product update(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setThumbnail(product.getThumbnail());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setProductType(product.getProductType());
        existingProduct.setUser(product.getUser());
        return productRepository.save(existingProduct);
    }

    @Override
    public Page<Product> searchProducts(Map<String, String> params) {
        Specification<Product> specification = new ProductSpecification();

        if (params.get("page") == null || params.get("pageSize") == null) {
            throw new RuntimeException("page and pageSize must be valued");
        }
        Integer page = Integer.valueOf(params.get("page"));
        Integer pageSize = Integer.valueOf(params.get("pageSize"));
        for (String parameterName : params.keySet()) {
            if (parameterName.equals("categoryId")) {

                List<Long> categoryIds = getCategoryIds(Long.valueOf(params.get(parameterName)));
                specification = specification.and(ProductSpecification.getSpecificationByParameterWithMultipleValues(parameterName, categoryIds));

            } else {
                specification = specification
                        .and(ProductSpecification.getSpecificationByParameter(parameterName, params.get(parameterName)));
            }
        }
        return productRepository.findAll(specification, PageRequest.of(page, pageSize));
    }



    private List<Long> getCategoryIds(Long parentCategoryId) {
        Category category = categoryRepository.findById(parentCategoryId).get();
        List<Long> categoryIds = new ArrayList<>();

        //retinem ID-ul categoriei
        categoryIds.add(category.getId());

        //verificam daca are subcategorie
        if (category.getSubCategories() != null) {
            List<Category> subCategories = category.getSubCategories();

            //iteram prin subcat si retinem id-ul ei
            subCategories.forEach(subcategory -> categoryIds.addAll(getCategoryIds(subcategory.getId())));
        }
        return categoryIds;
    }



    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);

    }
}
