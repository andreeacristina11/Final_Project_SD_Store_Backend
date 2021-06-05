package com.sda.store.controller.dto.order;

import com.sda.store.controller.dto.user.AddressDto;
import com.sda.store.model.Order;

import java.util.List;

public class OrderResponseDto {

    private Long id;
    private AddressDto addressDto;
    private Double totalPrice;
    private List<OrderLineDto> orderLines;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderLineDto> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLineDto> orderLines) {
        this.orderLines = orderLines;
    }
}
