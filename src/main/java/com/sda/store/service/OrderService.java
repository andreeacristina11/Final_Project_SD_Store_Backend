package com.sda.store.service;

import com.sda.store.model.Order;
import com.sda.store.model.OrderLine;

import java.util.List;

public interface OrderService {

    Order createOrder(List<OrderLine> orderLineList);
    List<Order> fndAllOrdersByUserId(Long id);
}
