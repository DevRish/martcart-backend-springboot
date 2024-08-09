package com.devrish.martcart.dto.requests.order;

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
public class AddOrderBody {

    @NotNull(message = "productId is required")
    @NotBlank(message = "productId cannot be blank")
    private String productId;

    @NotNull(message = "quantity is required")
    private Long quantity;

    @NotNull(message = "address is required")
    private String address;

}
