package com.example.vizsga_kedvenc_felugyelo.sec;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAtFilter implements Filter
{

    private final Logger LOG =
            Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
        System.out.println("Authentication Validation is in progress");
        chain.doFilter(request, response);
    }

}
