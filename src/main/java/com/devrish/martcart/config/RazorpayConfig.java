package com.devrish.martcart.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RazorpayConfig {

    @Value("${RAZORPAY_KEY_ID}")
    private String RAZORPAY_KEY_ID;

    @Value("${RAZORPAY_KEY_SECRET}")
    private String RAZORPAY_KEY_SECRET;

    @Bean
    public RazorpayClient razorpayClient() {
        try {
            RazorpayClient client = new RazorpayClient(RAZORPAY_KEY_ID,RAZORPAY_KEY_SECRET);
            log.info("Razorpay Client initialized successfully!");
            return client;
        } catch (RazorpayException e) {
            log.error("Couldn't initialize razorpay client!");
            throw new RuntimeException(e);
        }
    }

}
