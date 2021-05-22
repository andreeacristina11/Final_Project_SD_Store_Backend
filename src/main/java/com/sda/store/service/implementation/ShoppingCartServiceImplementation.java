package com.sda.store.service.implementation;

import com.sda.store.model.Product;
import com.sda.store.model.ShoppingCart;
import com.sda.store.repository.ShoppingCartRepository;
import com.sda.store.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImplementation implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImplementation(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart addProductToCart(Product product, ShoppingCart shoppingCart) {
        List<Product> existingShoppingList = shoppingCart.getProductList();
        existingShoppingList.add(product);
        shoppingCart.setProductList(existingShoppingList);
        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public ShoppingCart removeProductFromCart(Product product, ShoppingCart shoppingCart) {
        List<Product> existingShoppingList = shoppingCart.getProductList();
        List<Product> newProductList=
        existingShoppingList
                .stream()
                .filter(currentProductFromList -> !product.equals(currentProductFromList))
                .collect(Collectors.toList());
        shoppingCart.setProductList(newProductList);
        return shoppingCartRepository.save(shoppingCart);
    }
}
