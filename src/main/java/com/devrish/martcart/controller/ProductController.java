package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.product.CreateProductBody;
import com.devrish.martcart.dto.requests.product.GetProductsParams;
import com.devrish.martcart.dto.responses.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @GetMapping
    public GenericResponse getProducts(GetProductsParams params) {
        log.info(params.toString());
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @GetMapping("/{id}")
    public GenericResponse getProductById(@PathVariable String id) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping
    public GenericResponse createProduct(@RequestBody CreateProductBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
