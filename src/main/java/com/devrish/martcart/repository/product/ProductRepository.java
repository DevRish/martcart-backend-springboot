package com.devrish.martcart.repository.product;

import com.devrish.martcart.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String>, ProductRepositoryCustom {

}
