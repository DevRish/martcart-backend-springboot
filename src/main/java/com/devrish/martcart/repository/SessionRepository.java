package com.devrish.martcart.repository;

import com.devrish.martcart.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {
}
