package com.example.uade.tpo.controller;

import com.example.uade.tpo.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")//Create payment
    public ResponseEntity<String> createPayment(@RequestParam Long amount, @RequestParam String currency) {
        try {
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(amount, currency);
            return new ResponseEntity<>(paymentIntent.toJson(), HttpStatus.CREATED);
        } catch (StripeException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}