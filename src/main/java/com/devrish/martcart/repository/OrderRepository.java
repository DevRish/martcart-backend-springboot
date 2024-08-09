package com.devrish.martcart.repository;

import com.devrish.martcart.model.Order;
import com.devrish.martcart.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(User user);
}
