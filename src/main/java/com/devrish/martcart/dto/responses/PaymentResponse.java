package com.devrish.martcart.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponse extends GenericResponse {

    private String orderId;

    public PaymentResponse(Boolean status, String message, String orderId) {
        super(status, message);
        this.orderId = orderId;
    }
}
