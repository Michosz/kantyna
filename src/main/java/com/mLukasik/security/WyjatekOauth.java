package com.mLukasik.security;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = SerializerWyjatkuOauth.class)
public class WyjatekOauth extends OAuth2Exception 
{
    public WyjatekOauth(String msg) 
    {
        super(msg);
    }
}