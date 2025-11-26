package com.example.taskManager.utility;

import java.security.SecureRandom;
import java.util.Base64;

// security should not be hardcoded or save anywhere inside the public code
//this is for demo purpose . therefore i am generating from here and save it in a app.property file

public class JwtSecretKeyGenerator
{
    public static void main(String[] args)
    {
        byte[] key = new byte[64] ;

        new SecureRandom().nextBytes(key);

        System.out.println(Base64.getEncoder().encodeToString(key));

    }
}