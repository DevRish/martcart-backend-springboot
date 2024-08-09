package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.product.CreateProductBody;
import com.devrish.martcart.dto.requests.product.GetProductsParams;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.ProductResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public GenericResponse getProducts(GetProductsParams params) {
        log.info(params.toString());
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getCategoryById(
            @Valid @PathVariable @NotNull(message = "id is required") String id
    ) {
        try {
            ProductResponse res = productService.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch(ProductNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    GenericResponse.builder().status(false).message(e.getMessage()).build()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @PostMapping
    public GenericResponse createProduct(@RequestBody CreateProductBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
