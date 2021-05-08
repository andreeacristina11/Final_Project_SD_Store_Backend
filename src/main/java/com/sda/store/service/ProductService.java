package com.sda.store.service;

import com.sda.store.model.Product;
import com.sda.store.model.User;

public interface ProductService {
    Product create(Product product);
    Product findById(Long id);
    Product update(Product product);
    void delete (Long id);

}
