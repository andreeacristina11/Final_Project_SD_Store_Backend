package com.sda.store.controller;

import com.sda.store.controller.dto.user.ProductDto;
import com.sda.store.model.Product;
import com.sda.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping(value = "/products")
    public ProductDto create(@RequestBody ProductDto productDto){
        Product product = productService.create(mapProductDtoToProduct(productDto));
        return mapProductToProductDto(product);
    }

    @GetMapping(value = "/products/{id}")
    public ProductDto findById(@PathVariable("id") Long id){
        return mapProductToProductDto(productService.findById(id));
    }

    @PutMapping(value = "/products/{id}")
    public ProductDto update(@PathVariable("id") Long id, @RequestBody ProductDto productDto){
        Product updatedProduct = updateProductDtoToProduct(productService.findById(id), productDto);
        return mapProductToProductDto(productService.create(updatedProduct));
    }

    private ProductDto mapProductToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setThumbnail(product.getThumbnail());
        productDto.setCategory(product.getCategory());
        productDto.setProductType(product.getProductType());
        productDto.setUser(product.getUser());

        return productDto;
    }

    private Product mapProductDtoToProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setThumbnail(productDto.getThumbnail());
        product.setCategory(productDto.getCategory());
        product.setProductType(productDto.getProductType());
        product.setUser(productDto.getUser());

        return product;
    }

    private Product updateProductDtoToProduct(Product product, ProductDto productDto) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setThumbnail(productDto.getThumbnail());
        product.setCategory(productDto.getCategory());
        product.setProductType(productDto.getProductType());
        product.setUser(productDto.getUser());

        return product;
    }
}
