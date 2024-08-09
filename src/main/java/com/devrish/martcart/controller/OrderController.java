package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.order.AddOrderBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.OrderResponse;
import com.devrish.martcart.exception.cart.ProductNotFoundException;
import com.devrish.martcart.model.User;
import com.devrish.martcart.service.OrderService;
import com.devrish.martcart.util.validation.ValidationUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/getorderdata")
    public ResponseEntity<GenericResponse> getOrderData(@RequestAttribute(name = "currentUser") User currentUser) {
        try {
            OrderResponse res = orderService.getAll(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

    @PostMapping("/addOrder")
    public ResponseEntity<GenericResponse> addOrder(
            @Valid @RequestBody AddOrderBody body,
            @RequestAttribute(name = "currentUser") User currentUser,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) return ValidationUtils.generateValidationResult(bindingResult);
        try {
            OrderResponse res = orderService.addOrder(body, currentUser);
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

}
