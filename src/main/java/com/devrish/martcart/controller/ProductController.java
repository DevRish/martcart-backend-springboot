package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.product.GetProductsQuery;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.ProductResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.Product;
import com.devrish.martcart.model.User;
import com.devrish.martcart.service.ProductService;
import com.devrish.martcart.service.ValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ValidationService validationService;

    @GetMapping
    public ResponseEntity<GenericResponse> getProducts(GetProductsQuery reqQuery) {
        log.info(reqQuery.toString());
        try {
            ProductResponse res = productService.getAll(reqQuery);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse> getProductById(
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

    @GetMapping("/image/{id}")
    public ResponseEntity<?> getProductImageById(
            @Valid @PathVariable @NotNull(message = "id is required") String id
    ) {
        try {
            Resource res = productService.getImageById(id);
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(res);
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

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse> createProduct(
            @Valid Product product,
            @RequestPart("image") MultipartFile image,
            @RequestAttribute(name = "currentUser") User currentUser,
            BindingResult bindingResult
    ) {
        // log.info("Product: {}", product.toString());
        if (bindingResult.hasErrors()) return validationService.generateValidationResult(bindingResult);
        try {
            product.setSoldBy(currentUser);
            ProductResponse res = productService.createProduct(product, image);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

}
