package com.devrish.martcart.dto.responses;

import com.devrish.martcart.model.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse extends GenericResponse {

    private Order order;
    private List<Order> orders;

    public OrderResponse(Boolean status, String message, Order order, List<Order> orders) {
        super(status, message);
        this.order = order;
        this.orders = orders;
    }
}
