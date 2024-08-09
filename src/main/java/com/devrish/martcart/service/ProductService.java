package com.devrish.martcart.service;

import com.devrish.martcart.dto.responses.ProductResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.Product;
import com.devrish.martcart.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse getById(String id) throws Exception {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) throw new ProductNotFoundException();
        return new ProductResponse(
                true,
                "Product fetched successfully",
                product,
                null,
                1L
        );
    }

}
