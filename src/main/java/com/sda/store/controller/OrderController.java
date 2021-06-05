package com.sda.store.controller;


import com.sda.store.config.SecurityConfiguration;
import com.sda.store.controller.dto.order.OrderLineDto;
import com.sda.store.controller.dto.order.OrderResponseDto;
import com.sda.store.controller.dto.user.AddressDto;
import com.sda.store.model.Address;
import com.sda.store.model.Order;
import com.sda.store.model.OrderLine;
import com.sda.store.model.User;
import com.sda.store.service.OrderService;
import com.sda.store.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderController {

    private UserService userService;
    private OrderService orderService;

    public OrderController(UserService userService, OrderService orderService){
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping(path = "/orders")
    public List<OrderResponseDto> getOrders(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loogedUser = userService.findByEmail(userDetails.getUsername());
        List<Order> userOrders = orderService.fndAllOrdersByUserId(loogedUser.getId());
        List<OrderResponseDto> orderResponseDtos =
                userOrders.stream()
                            .map(this:: mapOrderToOrderResponseDto)
                            .collect(Collectors.toList());
        return orderResponseDtos;

    }

    public OrderResponseDto mapOrderToOrderResponseDto(Order order){
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());

        List<OrderLineDto> orderLineDtos =
                order

                .getOrderLineList()
                .stream()
                .map(orderLine -> mapOrderLineToOrderLineDto(orderLine))
                .collect(Collectors.toList());

        Address address = order.getUser().getAddress();

        dto.setOrderLines(orderLineDtos);
        dto.setAddressDto(AddressDto.mapAddressToAddressDto(address));
        dto.setTotalPrice(order.getTotal());

        return dto;
    }

    public OrderLineDto mapOrderLineToOrderLineDto(OrderLine orderLine){
        OrderLineDto orderLineDto = new OrderLineDto();
        orderLineDto.setProductName(orderLine.getProduct().getName());
        orderLineDto.setPrice(orderLine.getProduct().getPrice());
        orderLineDto.setQuantity(orderLine.getQuantity());
        return orderLineDto;
    }
}
