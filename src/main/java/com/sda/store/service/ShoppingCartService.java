package com.sda.store.service;

import com.sda.store.model.Product;
import com.sda.store.model.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCart addProductToCart(Product product, ShoppingCart shoppingCart);
    ShoppingCart removeProductFromCart(Product product, ShoppingCart shoppingCart);
}
