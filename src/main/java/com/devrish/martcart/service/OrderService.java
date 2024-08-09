package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.order.AddOrderBody;
import com.devrish.martcart.dto.responses.OrderResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.Order;
import com.devrish.martcart.model.Product;
import com.devrish.martcart.model.User;
import com.devrish.martcart.repository.OrderRepository;
import com.devrish.martcart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderResponse getAll(User currentUser) throws Exception {
        List<Order> orders = orderRepository.findByUserId(currentUser);
        orders.forEach(order -> {
            User oUser = order.getUserId();
            order.setUserId(User.builder().firstname(oUser.getFirstname()).lastname(oUser.getLastname()).build());
            Product oProduct = order.getProductId();
            User soldBy = oProduct.getSoldBy();
            oProduct.setSoldBy(User.builder().firstname(soldBy.getFirstname()).lastname(soldBy.getLastname()).build());
        });
        return new OrderResponse(
                true,
                "Orders fetched successfully",
                null,
                orders
        );
    }

    public OrderResponse addOrder(AddOrderBody body, User currentUser) throws Exception {
        Product product = productRepository.findById(body.getProductId().toString()).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        Order order = Order.builder()
                .productId(product)
                .quantity(body.getQuantity())
                .address(body.getAddress())
                .userId(currentUser)
                .totalPrice(product.getCurrentPrice() * body.getQuantity())
                .orderedAt(new Date())
                .build();
        orderRepository.save(order);
        order.setUserId(
                User.builder()
                        .firstname(currentUser.getFirstname())
                        .lastname(currentUser.getLastname())
                        .build()
        );
        User soldBy = product.getSoldBy();
        product.setSoldBy(User.builder().firstname(soldBy.getFirstname()).lastname(soldBy.getLastname()).build());
        order.setProductId(product);
        return new OrderResponse(
                true,
                "Order placed successfully",
                order,
                null
        );
    }

}
