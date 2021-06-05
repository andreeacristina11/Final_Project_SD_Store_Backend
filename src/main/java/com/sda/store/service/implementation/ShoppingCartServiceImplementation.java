package com.sda.store.service.implementation;

import com.sda.store.excpetion.ResourceAlreadyPresentInDatabase;
import com.sda.store.model.Product;
import com.sda.store.model.ShoppingCart;
import com.sda.store.repository.ShoppingCartRepository;
import com.sda.store.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImplementation implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImplementation(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public ShoppingCart addProductToCart(Product product, ShoppingCart shoppingCart) {
        if(shoppingCart.getProductList().contains(product)){
            throw new ResourceAlreadyPresentInDatabase(String.format("Product wiht  %d is alrdeay in cart", product.getId()));
        }
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

    @Override
    public void clearShoppingCart(Long shoppingCartId) {
        Optional<ShoppingCart> shoppingCart  = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCart.isPresent()){
            ShoppingCart shoppingCartInDb = shoppingCart.get();
            shoppingCartInDb.setProductList(new ArrayList<>());
            shoppingCartRepository.save(shoppingCartInDb);
        }
    }
}
