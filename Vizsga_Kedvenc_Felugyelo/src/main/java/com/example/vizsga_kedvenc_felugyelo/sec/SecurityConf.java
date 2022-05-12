package com.example.vizsga_kedvenc_felugyelo.sec;

import com.example.vizsga_kedvenc_felugyelo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;


@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter
{

    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private JwtTokenGeneratorFilter filter;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/user/add").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .headers().frameOptions().disable()
                .and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                        .addFilterBefore(new RequestValidationBeforeFilter(authenticationManagerBean()), BasicAuthenticationFilter.class)
                        .addFilterBefore(new JwtTokenValidatorFilter(), BasicAuthenticationFilter.class)
                        .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                        .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
                        .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class).httpBasic();
    }


}
