package com.devrish.martcart.repository;

import com.devrish.martcart.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
