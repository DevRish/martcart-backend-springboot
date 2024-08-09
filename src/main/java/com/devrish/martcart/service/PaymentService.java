package com.devrish.martcart.service;

import com.devrish.martcart.dto.requests.payment.CreateRazorpayOrderBody;
import com.devrish.martcart.dto.responses.PaymentResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentService {

    @Autowired
    private RazorpayClient razorpayClient;

    public PaymentResponse createRazorpayOrder(CreateRazorpayOrderBody body) throws RazorpayException {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount",body.getAmount());
        orderRequest.put("currency","INR");
        Order razorpayOrder = razorpayClient.orders.create(orderRequest);
        return new PaymentResponse(
                true,
                "Successfully created new order",
                razorpayOrder.get("id")
        );
    }

}
