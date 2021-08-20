package me.widua.jwtauthorization.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.widua.jwtauthorization.models.JwtAuthorizationModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager ;

    private final JwtConfig jwtConfig = new JwtConfig() ;

    public JwtAuthenticationFilter ( AuthenticationManager manager ) {
        this.manager = manager;
        setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try{
            JwtAuthorizationModel user = new ObjectMapper().readValue(request.getInputStream() , JwtAuthorizationModel.class) ;
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()) ;
            return manager.authenticate(authentication);
        } catch (IOException e){
            throw new RuntimeException("Filter can't read request! "+e) ;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .setExpiration(jwtConfig.getExpireDate())
                .signWith(Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes()))
                .compact();
        response.addHeader("Authorization","Bearer "+token);

    }
}
