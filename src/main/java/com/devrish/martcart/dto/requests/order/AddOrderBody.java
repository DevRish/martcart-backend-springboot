package com.devrish.martcart.dto.requests.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderBody {

    private String productId;
    private Long quantity;
    private String address;

}
