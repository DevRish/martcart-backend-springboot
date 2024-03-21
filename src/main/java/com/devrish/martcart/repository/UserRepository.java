package com.devrish.martcart.repository;

import com.devrish.martcart.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
