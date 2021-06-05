package com.sda.store.repository;

import com.sda.store.model.Category;
import com.sda.store.model.Product;
import com.sda.store.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.sound.sampled.Port;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static Specification<Product> withCategoryId(Long categoryId) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<Product, Category> categoryJoin = root.join("category");
            return criteriaBuilder.equal(categoryJoin.get("id"), categoryId);
        };
    }

    public static Specification<Product> withCategoryIdsIn(List<Long> categoryIds){
        return (root, criteriaQuery, criteriaBuilder) ->{
            Join<Product, Category> cateogoryJoin = root.join("category");
            return cateogoryJoin.get("id").in(categoryIds);
        };
    }


    public static Specification<Product> getSpecificationByParameter(String parameterNamer, String parameterValue ){
        switch (parameterNamer){
            case "name" : return  withNameLike(parameterValue);
            case "productType": return ofType(ProductType.valueOf(parameterValue));
            case "categoryId": return withCategoryId(Long.valueOf(parameterValue));
            case "price":
                String price = parameterValue; // lowPrice:50,highPrice:100; // price=highPrice:100,lowPrice:50
                Double lowPrice = 0.0;
                Double highPrice = Double.MAX_VALUE;
                if (price.contains(",")) {
                    List<String> prices = Arrays.asList(price.split(",")); // lowPrice:50 | highPrice:100
                    for (String iteration: prices) {
                        if (iteration.contains("lowPrice")) {
                            String lowPriceInStringFormat = iteration.replaceAll("lowPrice:", ""); // lowPrice:50  ===> 50;
                            lowPrice = Double.valueOf(lowPriceInStringFormat);
                        } else if (iteration.contains("highPrice")) {{
                            String highPriceInStringFormat = iteration.replaceAll("highPrice:", ""); // highPrice:100 ==> 100;
                            highPrice = Double.valueOf(highPriceInStringFormat);
                        }}
                    }
                }
                return withPriceInRange(lowPrice, highPrice);
            default:return new ProductSpecification();
        }
    }

    public static Specification<Product> getSpecificationByParameterWithMultipleValues(String parameterName, List<Long> ids){
        switch (parameterName){
            case "categoryId": return withCategoryIdsIn(ids);
            default: return new ProductSpecification();
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
