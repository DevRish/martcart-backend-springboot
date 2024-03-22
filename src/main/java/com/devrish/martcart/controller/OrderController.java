package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.order.AddOrderBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @GetMapping("/getorderdata")
    public GenericResponse getOrderData() {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/addOrder")
    public GenericResponse addOrder(@RequestBody AddOrderBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
