package com.sda.store.repository;

import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
    Product findByPrice(Double price);
    Product findByProductType(ProductType productType);
}
