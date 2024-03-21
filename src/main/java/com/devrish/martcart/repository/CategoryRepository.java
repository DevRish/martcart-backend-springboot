package com.devrish.martcart.repository;

import com.devrish.martcart.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}
