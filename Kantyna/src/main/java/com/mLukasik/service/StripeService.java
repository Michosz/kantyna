package com.mLukasik.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.mLukasik.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class StripeService 
{
	@Value("${stripe.secret.key}")
	String secretKey;
    //private String secretKey = "sk_test_Rm6gpucke3MdSIScPUj4FpGN00crJvI8F0";
     
    @PostConstruct
    public void init() 
    {
        Stripe.apiKey = secretKey;
    }
    
    public Charge charge(ChargeRequest chargeRequest) throws AuthenticationException, StripeException 
    {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}