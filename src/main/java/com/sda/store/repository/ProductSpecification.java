package com.sda.store.repository;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ProductSpecification implements Specification<Product> {

    public static Specification<Product> withNameLike (String productName){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + productName + "%");
    }

    public static Specification<Product> ofType (ProductType productType){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("productType"), productType);
    }

    public static Specification<Product> withPriceInRange (Double lowerInterval, Double higherinterval){
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("price"), lowerInterval, higherinterval);
    }

    public static Specification<Product> withCategoryId (Long categoryId){
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Product> getSpecificationByParameter(String parameterNamer, String parameterValue ){
        switch (parameterNamer){
            case "name" : return  withNameLike(parameterValue);
            case "productType": return ofType(ProductType.valueOf(parameterValue));
            case "categoryId": return withCategoryId(Long.valueOf(parameterValue));
            case "price":
                List<String> pricerRanger = Arrays.asList(parameterValue.split(":"));
                if(pricerRanger.size() ==1){
                    return withPriceInRange(Double.valueOf(pricerRanger.get(0)), Double.MAX_VALUE);
                }
                return withPriceInRange(Double.valueOf(pricerRanger.get(0)), Double.valueOf(pricerRanger.get(1)));
            default:return new ProductSpecification();
        }
    }

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return null;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
