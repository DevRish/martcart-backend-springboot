package com.devrish.martcart.dto.requests.cart;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrRemoveItemBody {

    @NotNull(message = "productId is required")
    @NotBlank(message = "productId cannot be blank")
    private String productId;

}