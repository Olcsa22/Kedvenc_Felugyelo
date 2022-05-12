package com.example.vizsga_kedvenc_felugyelo.sec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenValidatorFilter extends OncePerRequestFilter
{


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        String jwt = request.getHeader("Authorization");
        if (null != jwt) {
            System.out.println("Jwt: "+jwt);
            try {
                SecretKey key = Keys.hmacShaKeyFor(
                        "Darth Vader szereti az epres  tejet".getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();
                String username = String.valueOf(claims.get("username"));
                String authorities = (String) claims.get("authorities");
                Authentication auth = new UsernamePasswordAuthenticationToken(username,null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }catch (Exception e) {
                //System.out.println("Hibás jwt token");
            }

        }
        chain.doFilter(request, response);
    }


    @Override protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/api/login"); }


}
