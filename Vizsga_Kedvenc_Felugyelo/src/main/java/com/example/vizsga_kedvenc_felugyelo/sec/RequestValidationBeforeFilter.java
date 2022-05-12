package com.example.vizsga_kedvenc_felugyelo.sec;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.StringUtils;

public class RequestValidationBeforeFilter implements Filter
{

    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private Charset credentialsCharset = StandardCharsets.UTF_8;
    private BasicAuthenticationConverter authenticationConverter=new BasicAuthenticationConverter();
    @Autowired
    private final AuthenticationManager authenticationManager;


    public RequestValidationBeforeFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header = req.getHeader(AUTHORIZATION);
        if (header != null)
        {
            header = header.trim();
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC))
            {
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                /*if(header.contains(","))
                    base64Token=header.substring(6).split(",")[0].getBytes(StandardCharsets.UTF_8);*/
                byte[] decoded;
                try
                {
                    decoded = Base64.getDecoder().decode(base64Token);

                    String token = new String(decoded, getCredentialsCharset(req));
                    int delim = token.indexOf(":");
                    if (delim == -1)
                    {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    String email = token.substring(0, delim);
                    if (email.toLowerCase().contains("test"))
                    {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException e)
                {
                    System.out.println("Hiba a basic authentication token dekódolása közben");
                    /*((HttpServletResponse) response).setHeader("Authorization",null);
                    ((HttpServletRequest) request).getSession().setAttribute("userdetails",null);*/
                }
                UsernamePasswordAuthenticationToken authenticationToken = this.authenticationConverter.convert(req);
                String username = authenticationToken.getName();
                Authentication authResult;
                    authResult = this.authenticationManager.authenticate(authenticationToken);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authResult);
                SecurityContextHolder.setContext(context);
            }


        }

        chain.doFilter(request, response);
    }

    protected Charset getCredentialsCharset(HttpServletRequest request)
    {
        return getCredentialsCharset();
    }

    public Charset getCredentialsCharset()
    {
        return this.credentialsCharset;
    }
}