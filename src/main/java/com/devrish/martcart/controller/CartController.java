package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.cart.AddOrRemoveItemBody;
import com.devrish.martcart.dto.responses.CartResponse;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.User;
import com.devrish.martcart.service.CartService;
import com.devrish.martcart.service.ValidationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ValidationService validationService;

    @GetMapping("/getCart")
    public ResponseEntity<GenericResponse> getCart(@RequestAttribute(name = "currentUser") User currentUser) {
        try {
            CartResponse res = cartService.getCart(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<GenericResponse> addItem(
            @Valid @RequestBody AddOrRemoveItemBody body,
            @RequestAttribute(name = "currentUser") User currentUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return validationService.generateValidationResult(bindingResult);
        try {
            CartResponse res = cartService.addItemToCart(body, currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (ProductNotFoundException e) {
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

    @PostMapping("/removeItem")
    public ResponseEntity<GenericResponse> removeItem(
            @Valid @RequestBody AddOrRemoveItemBody body,
            @RequestAttribute(name = "currentUser") User currentUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return validationService.generateValidationResult(bindingResult);
        try {
            CartResponse res = cartService.removeItemFromCart(body, currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (ProductNotFoundException e) {
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

    @PostMapping("/emptyCart")
    public ResponseEntity<GenericResponse> emptyCart(@RequestAttribute(name = "currentUser") User currentUser) {
        try {
            CartResponse res = cartService.emptyCart(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

}
