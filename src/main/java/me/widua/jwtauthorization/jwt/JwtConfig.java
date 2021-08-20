package me.widua.jwtauthorization.jwt;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


public class JwtConfig {

    private String secret = "jWnZr4t7w!z%C*F-JaNdRgUkXp2s5v8x";

    public JwtConfig ( String secret ){
        this.secret = secret;
    }

    public JwtConfig(){}

    public String getSecret() {
        return secret;
    }

    public Date getExpireDate (){
        return new Date( System.currentTimeMillis() + 1209599990 ) ;
    }

}
