package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.payment.CreateRazorpayOrderBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import com.devrish.martcart.dto.responses.PaymentResponse;
import com.devrish.martcart.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/createOrder")
    public ResponseEntity<GenericResponse> createRazorpayOrder(@RequestBody CreateRazorpayOrderBody body) {
        try {
            PaymentResponse res = paymentService.createRazorpayOrder(body);
            return ResponseEntity.status(HttpStatus.OK).body(res);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    GenericResponse.builder().status(false).message("Server Error").build()
            );
        }
    }

}