package com.sda.store.model;

import com.sun.xml.bind.v2.TODO;

import javax.persistence.*;
import java.util.Locale;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 255)
    private String description;
    private String thumbnail ;

    @ManyToOne
    private Category category; //TODO

    @Column(nullable = false)
    private Double price;

    @Enumerated(value = EnumType.STRING)
    private ProductType productType;

    @ManyToOne
    private User user; //TODO

    public Product() {
    }

    public Product(String name, String description, String thumbnail, Category category, Double price, ProductType productType, User user) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.category = category;
        this.price = price;
        this.productType = productType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
