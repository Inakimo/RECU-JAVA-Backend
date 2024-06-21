package com.example.uade.tpo.service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams Params =
                PaymentIntentCreateParams.builder()
                        .setCurrency(currency)
                        .setAmount(amount)
                        .build();

        return PaymentIntent.create(Params);
    }
}
