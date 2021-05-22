package com.sda.store.service;

import com.sda.store.model.Product;
import com.sda.store.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product create(Product product);
    Product findById(Long id);
    Product update(Product product);
    void delete (Long id);
    Page<Product> searchProducts(Map<String, String> params);

}
