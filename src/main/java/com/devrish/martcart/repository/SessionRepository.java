package com.devrish.martcart.repository;

import com.devrish.martcart.model.Session;
import com.devrish.martcart.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUserId(User user);
}
