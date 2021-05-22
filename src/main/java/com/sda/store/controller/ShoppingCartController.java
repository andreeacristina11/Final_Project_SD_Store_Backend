package com.sda.store.controller;

import com.sda.store.config.SecurityConfiguration;
import com.sda.store.controller.dto.shoppingCart.ProductShoppingCartResponseDto;
import com.sda.store.controller.dto.shoppingCart.ShoppingCartResponseDto;
import com.sda.store.model.Product;
import com.sda.store.model.ShoppingCart;
import com.sda.store.model.User;
import com.sda.store.service.ProductService;
import com.sda.store.service.ShoppingCartService;
import com.sda.store.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;
    private ProductService productService;
    private UserService userService;

    public ShoppingCartController(ShoppingCartService shoppingCartService, ProductService productService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.userService = userService;
    }


    @PutMapping(path = "/shopping-cart")
    public ShoppingCartResponseDto addProductToCart(@RequestBody Long productId){
        UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(loggedUser.getUsername());
        Product product = productService.findById(productId);
        ShoppingCart shoppingCart = shoppingCartService.addProductToCart(product, user.getShoppingCart());
        return mapShoppingCartToResponseDto(shoppingCart);
    }

    @GetMapping(path = "/shopping-cart")
    public ShoppingCartResponseDto getShoppingCartResponse(){
        UserDetails loggedUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByEmail(loggedUser.getUsername());
        return mapShoppingCartToResponseDto(user.getShoppingCart());
    }

    public ShoppingCartResponseDto mapShoppingCartToResponseDto(ShoppingCart shoppingCart){
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        List<ProductShoppingCartResponseDto> productShoppingCartResponseDtoList = new ArrayList<>();

        for (Product product: shoppingCart.getProductList()){
            ProductShoppingCartResponseDto dto = new ProductShoppingCartResponseDto();
            dto.setId(product.getId());
            dto.setProductName(product.getName());
            productShoppingCartResponseDtoList.add(dto);
        }

        shoppingCartResponseDto.setProductsInCart(productShoppingCartResponseDtoList);
        return shoppingCartResponseDto;

    }

}
