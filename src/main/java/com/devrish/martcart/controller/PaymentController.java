package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.payment.CreateRazorpayOrderBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/createOrder")
    public GenericResponse createRazorpayOrder(@RequestBody CreateRazorpayOrderBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}