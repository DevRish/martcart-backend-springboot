package com.devrish.martcart.controller;

import com.devrish.martcart.dto.requests.cart.AddOrRemoveItemBody;
import com.devrish.martcart.dto.responses.GenericResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @GetMapping("/getCart")
    public GenericResponse getCart() {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/addItem")
    public GenericResponse addItem(@RequestBody AddOrRemoveItemBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/removeItem")
    public GenericResponse removeItem(@RequestBody AddOrRemoveItemBody body) {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

    @PostMapping("/emptyCart")
    public GenericResponse emptyCart() {
        return GenericResponse.builder().status(true).message("Route hit successfully").build();
    }

}
